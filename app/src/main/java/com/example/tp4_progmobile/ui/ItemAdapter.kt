package com.example.tp4_progmobile.ui

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4_progmobile.ImageProvider
import com.example.tp4_progmobile.databinding.ItemRowBinding
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.dialog.EditDialog
import com.google.firebase.firestore.FirebaseFirestore

/**
 * L'adapteur d'un item de la base de donnée
 */
class ItemAdapter(private var items: MutableList<Item>, private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var parentContext: Activity
    private var db = FirebaseFirestore.getInstance()

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

        /**
         * Gestion du clique sur un item (Modifier, Supprimer)
         */
        holder.itemView.setOnClickListener {
            val options = arrayOf("Modifier", "Supprimer")

            val alertDialog = AlertDialog.Builder(parentContext)
                .setTitle("Options")
                .setItems(options) { aDialog, which ->
                    when (which) {
                        0 -> {
                            showEditDialog(currentItem, position)
                            aDialog.dismiss()
                        }
                        1 -> {
                            val confOptions = arrayOf("Oui", "Non")
                            val confirmationDialog = AlertDialog.Builder(parentContext)
                                .setTitle("Voulez-vous supprimer \"${currentItem.nom}\" ?")
                                .setItems(confOptions) { dialog, choix ->
                                    when(choix){
                                        0 ->{
                                            val itemRef = db.collection("items").document(currentItem.id!!)
                                            itemRef.delete()
                                                .addOnSuccessListener {
                                                    Toast.makeText(parentContext, "L'item a été supprimé avec succès!", Toast.LENGTH_SHORT).show()

                                                    removeItem(currentItem, position)
                                                    dialog.dismiss()
                                                    aDialog.dismiss()
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(parentContext, "Erreur lors de la suppression de l'item.", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                        1 -> {
                                            dialog.cancel()
                                        }
                                    }
                                }
                            confirmationDialog.show()
                        }
                    }
                }
                .create()

            alertDialog.show()
        }
    }

    /**
     * Affiche le dialog d'édition d'item
     */
    private fun showEditDialog(item: Item, position: Int) {
        val editDialog = EditDialog(item, position, this@ItemAdapter, parentContext)
        editDialog.show(fragmentManager, "editDialog")
    }

    /**
     * Modifie l'item à une certaine position
     */
    fun updateData(item: Item, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    /**
     * Supprime un item à une certaine position
     */
    fun removeItem(item: Item, position: Int){
        items.remove(item)
        notifyItemRemoved(position)
    }

    /**
     * Retourne le nom d'item dans le RecyclerView
     */
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)
}


