package com.example.tp4_progmobile.ui.ajouter

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.tp4_progmobile.R
import com.example.tp4_progmobile.databinding.FragmentAjouterBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment ajouter un item
 */
class AjouterFragment : Fragment() {

    private var _binding: FragmentAjouterBinding? = null
    private val binding get() = _binding!!

    private val notificationsViewModel: AjouterViewModel by viewModels()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjouterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textViewAjouter.let { textView ->
            notificationsViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
        }

        binding.btnAjouter.setOnClickListener {
            ajouterItem()
        }

        return root
    }

    /**
     * Gère la création du nouvel item et l'ajoute dans Firebase
     */
    private fun ajouterItem() {

        val nom = binding.edNom.text.toString()
        val categorie = binding.spinnerCategorie?.selectedItemPosition
        val prix = binding.edPrix.text.toString().toDoubleOrNull()

        if (prix == null || nom.isEmpty()) {
            Toast.makeText(requireContext(), "Remplissez tous les champs pour ajouter un item!", Toast.LENGTH_SHORT).show()
        } else {
            val item = hashMapOf(
                "nom" to nom,
                "categorie" to categorie,
                "prix" to prix
            )

            db.collection("items")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

                    createNotification()

                    val action: NavDirections = AjouterFragmentDirections.actionNavAjouterToNavMagasin()
                    Navigation.findNavController(requireView()).navigate(action)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(requireContext(), "Erreur lors de l'ajout d'un item!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Crée la notification suite à l'ajout d'un item
     */
    private fun createNotification() {
        val channelId = getString(R.string.notif_id)
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Nouvel item ajouté")
            .setContentText("Un nouvel item a été ajouté au magasin.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                val notificationId = 1
                notify(notificationId, builder.build())
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
