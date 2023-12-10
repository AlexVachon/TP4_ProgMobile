package com.example.tp4_progmobile.ui.ajouter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel du fragment AjouterFragment.
 * Affiche le titre de la page "Ajouter un item"
 */
class AjouterViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ajouter un item"
    }
    val text: LiveData<String> = _text
}

