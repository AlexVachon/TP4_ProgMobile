package com.example.tp4_progmobile.ui

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4_progmobile.ImageProvider
import com.example.tp4_progmobile.databinding.ItemRowBinding
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.dialog.EditDialog

class ItemAdapter(private var items: MutableList<Item>, private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var parentContext: Activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        parentContext = parent.context as Activity
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parentContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.tvNom.text = currentItem.nom
        holder.binding.tvCategorie.text = ImageProvider.getCategoryAtIndex(currentItem.categorie!!)
        holder.binding.ivLocCat.setImageResource(ImageProvider.getImageAtIndex(currentItem.categorie!!))
        "${currentItem.prix.toString()}$".also { holder.binding.tvPrix.text = it }

        holder.itemView.setOnClickListener {
            val options = arrayOf("Modifier", "Supprimer")

            val alertDialog = AlertDialog.Builder(parentContext)
                .setTitle("Options")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            // Option "Modifier" sélectionnée
                            showEditDialog(currentItem, position)
                        }
                        1 -> {
                            // Option "Supprimer" sélectionnée
                            // TODO: Implémentez votre logique de suppression ici
                        }
                    }
                }
                .create()

            alertDialog.show()
        }
    }

    private fun showEditDialog(item: Item, position: Int) {
        val editDialog = EditDialog(item, position, this@ItemAdapter)
        editDialog.show(fragmentManager, "editDialog")
    }

    fun updateData(item: Item, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}


