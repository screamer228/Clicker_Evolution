package com.example.clickerevolution.presentation.dialog_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository

class DialogViewModelFactory(
    private val prefsRepository: PrefsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DialogViewModel(
                prefsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}