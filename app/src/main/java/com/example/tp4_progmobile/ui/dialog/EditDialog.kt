package com.example.tp4_progmobile.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.tp4_progmobile.R
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.ItemAdapter
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Dialog pour modifier un item
 */
class EditDialog(
    private val currentItem: Item,
    private val position: Int,
    private val adapter: ItemAdapter,
    private val context: Context
) : DialogFragment() {

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_edit_item_dialog, null)

        val edtName = view.findViewById<EditText>(R.id.edtName)
        val edtPrice = view.findViewById<EditText>(R.id.edtPrice)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)

        edtName?.setText(currentItem.nom)
        edtPrice?.setText(currentItem.prix.toString())
        currentItem.categorie?.let {
            spinnerCategory?.setSelection(it)
        }
        builder?.setView(view)
            /**
             * Au clique du bouton "OK", modifie l'item sélectionné et
             * notifie l'adapteur du changement
             */
            ?.setPositiveButton("OK") { dialog, id ->
                val newNom = edtName.text.toString()
                val newPrix = edtPrice.text.toString().toDoubleOrNull() ?: 0.0
                val newCategory = spinnerCategory.selectedItemPosition

                if (newNom.isEmpty()) {
                    Toast.makeText(context, "Vous devez remplir tous les champs!", Toast.LENGTH_SHORT).show()
                } else {
                    val itemRef = db.collection("items").document(currentItem.id!!)
                    val updates = mutableMapOf<String, Any>()

                    updates["nom"] = newNom
                    updates["categorie"] = newCategory
                    updates["prix"] = newPrix

                    itemRef.update(updates)
                        .addOnSuccessListener {
                            Toast.makeText(context, "L'item a été modifié avec succès!", Toast.LENGTH_SHORT).show()
                            val updatedItem = Item(
                                currentItem.id,
                                newNom,
                                newCategory,
                                newPrix
                            )
                            adapter.updateData(updatedItem, position)
                            dialog.dismiss()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Erreur lors de la modification de l'item.", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            /**
             * Au clique du bouton "Annuler", ferme le dialog
             */
            ?.setNegativeButton("Annuler") { dialog, id ->
                getDialog()?.cancel()
            }

        return builder?.create() ?: super.onCreateDialog(savedInstanceState)
    }
}

