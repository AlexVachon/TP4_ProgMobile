package com.example.tp4_progmobile.ui.notifications

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tp4_progmobile.databinding.FragmentAjouterBinding
import com.example.tp4_progmobile.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class AjouterFragment : Fragment() {

    private var _binding: FragmentAjouterBinding? = null
    private val binding get() = _binding!!

    // Use viewModels() delegate to get an instance of the ViewModel
    private val notificationsViewModel: AjouterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjouterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Use null-safe access to avoid NullPointerException
        binding.textViewAjouter?.let { textView ->
            notificationsViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
        }

        binding.btnAjouter.setOnClickListener {
            ajouterItem()
        }

        return root
    }

    private fun ajouterItem() {
        var db = FirebaseFirestore.getInstance()

        val nom = binding.edNom.text.toString()

        val categorie = Random.nextInt(6) + 1
        val prix = binding.edPrix.text.toString().toDoubleOrNull()

        val item = hashMapOf(
            "nom" to nom,
            "categorie" to categorie,
            "prix" to prix
        )

        db.collection("items")
            .add(item)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Item ajouté avec succès!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks
        _binding = null
    }
}
