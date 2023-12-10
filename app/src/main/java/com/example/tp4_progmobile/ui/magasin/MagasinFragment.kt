package com.example.tp4_progmobile.ui.magasin


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp4_progmobile.AuthentificationActivity
import com.example.tp4_progmobile.databinding.FragmentMagasinBinding
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.ItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment magasin qui va chercher les items de la base de donnée
 * pour les afficher à l'aide de l'adapteur
 */
class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private val binding get() = _binding!!

    private var db = FirebaseFirestore.getInstance()
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMagasinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "Déconnecté avec succès!", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), AuthentificationActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemList: MutableList<Item> = mutableListOf()
        db.collection("items")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    val item = Item(
                        document.id,
                        document.get("nom").toString(),
                        document.get("categorie").toString().toInt(),
                        document.get("prix").toString().toDouble()
                    )
                    itemList.add(item)
                }
                itemAdapter = ItemAdapter(itemList, childFragmentManager)
                recyclerView.adapter = itemAdapter
            }
            .addOnFailureListener{
                Log.w("FireBase", "Erreur lors de la récupération des documents.", it)
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
