package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.PrefsRepository
import com.example.clickerevolution.data.repository.SkinsRepository

class SharedViewModelFactory(
    private val prefsRepository: PrefsRepository,
    private val skinsRepository: SkinsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(
                prefsRepository,
                skinsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}