package com.example.tp4_progmobile.dialog

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.tp4_progmobile.R
import com.example.tp4_progmobile.model.Item
import com.example.tp4_progmobile.ui.ItemAdapter

class EditDialog(val currentItem: Item) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = activity?.let { AlertDialog.Builder(it)}
        val inflater = requireActivity().layoutInflater


        builder?.setView(inflater.inflate(R.layout.fragment_edit_item_dialog, null))
            ?.setPositiveButton("OK") { dialog, id ->

            }
            ?.setNegativeButton("Annuler") { dialog, id ->
                getDialog()?.cancel()
            }

        super.onCreate(savedInstanceState)
    }
}