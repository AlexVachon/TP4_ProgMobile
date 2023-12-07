package com.example.tp4_progmobile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4_progmobile.ImageProvider
import com.example.tp4_progmobile.databinding.ItemRowBinding
import com.example.tp4_progmobile.model.Item

class ItemAdapter(private val itemList: List<Item>, private val onItemClick: OnItemClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

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

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}

