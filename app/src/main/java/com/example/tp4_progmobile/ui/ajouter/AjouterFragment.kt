package com.example.tp4_progmobile.ui.ajouter

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tp4_progmobile.databinding.FragmentAjouterBinding
import com.google.firebase.firestore.FirebaseFirestore

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

        val nom = binding.edNom.text.toString()

        val categorie = binding.spinnerCategorie?.selectedItemPosition

        val prix = binding.edPrix.text.toString().toDoubleOrNull()

        if(prix == null || nom.isEmpty()){
            Toast.makeText(requireContext(), "Remplissez tous les champs pour ajouter un item!", Toast.LENGTH_SHORT).show()
        }else{
            val item = hashMapOf(
                "nom" to nom,
                "categorie" to categorie,
                "prix" to prix
            )

            db.collection("items")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(requireContext(), "\"${item["nom"]}\" ajouté avec succès!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(requireContext(), "Erreur lors de l'ajout d'un item!", Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks
        _binding = null
    }
}