// HomeFragment.kt

package com.example.tp4_progmobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp4_progmobile.databinding.FragmentHomeBinding
import com.example.tp4_progmobile.model.Item // Remplacez par le vrai modèle de vos données
import com.example.tp4_progmobile.ui.ItemAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Créez une liste d'exemple (remplacez-la par votre liste réelle)
        val itemList: List<Item> = listOf(
            Item("Nom1", "Catégorie1", 1, 10.0),
            Item("Nom2", "Catégorie2", 2, 20.0),
            // Ajoutez d'autres éléments
        )

        val adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
