package com.example.tp4_progmobile.ui

// ItemAdapter.kt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4_progmobile.ImageProvider
import com.example.tp4_progmobile.databinding.ItemRowBinding
import com.example.tp4_progmobile.model.Item // Remplacez par le vrai modèle de vos données

class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.binding.tvNom.text = currentItem.nom
        holder.binding.tvCategorie.text = ImageProvider.getCategoryAtIndex(currentItem.categorie!!)
        holder.binding.ivLocCat.setImageResource(ImageProvider.getImageAtIndex(currentItem.categorie!!))
        "${currentItem.prix.toString()}$".also { holder.binding.tvPrix.text = it }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}
