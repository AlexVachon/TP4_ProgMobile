package com.example.tp4_progmobile.ui.magasin


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp4_progmobile.databinding.FragmentMagasinBinding
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.ItemAdapter
import com.google.firebase.firestore.FirebaseFirestore

class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private val binding get() = _binding!!

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val magasinViewModel =
            ViewModelProvider(this)[MagasinViewModel::class.java]

        _binding = FragmentMagasinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemList: MutableList<Item> = mutableListOf()
        db.collection("items")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    val item = document.toObject(Item::class.java)
                    itemList.add(item)
                }
                val adapter = ItemAdapter(itemList)
                recyclerView.adapter = adapter
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
