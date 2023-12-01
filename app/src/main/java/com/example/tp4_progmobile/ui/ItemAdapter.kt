package com.example.tp4_progmobile.ui

// ItemAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4_progmobile.databinding.ItemRowBinding
import com.example.tp4_progmobile.model.Item // Remplacez par le vrai modèle de vos données

class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        // Mettez à jour les vues avec les données de votre modèle
        holder.binding.tvNom.text = currentItem.nom // Remplacez par les vrais noms de vos propriétés
        holder.binding.tvCategorie.text = currentItem.category.toString()
        holder.binding.tvPrix.text = currentItem.prix.toString()
        // Assurez-vous de charger l'image si vous avez une ImageView
        // holder.binding.ivLocCat.setImageResource(currentItem.imageResourceId)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}
