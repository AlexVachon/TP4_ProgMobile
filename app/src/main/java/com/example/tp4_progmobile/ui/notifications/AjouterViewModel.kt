package com.example.tp4_progmobile.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AjouterViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ajouter un item"
    }
    val text: LiveData<String> = _text
}

