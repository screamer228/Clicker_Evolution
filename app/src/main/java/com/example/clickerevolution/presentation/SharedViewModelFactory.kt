package com.example.clickerevolution.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.PrefsRepository

class SharedViewModelFactory(
    private val prefsRepository: PrefsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(
                prefsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}