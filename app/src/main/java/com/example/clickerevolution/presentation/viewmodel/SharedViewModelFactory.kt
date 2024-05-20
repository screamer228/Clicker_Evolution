package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository

class SharedViewModelFactory(
    private val prefsRepository: PrefsRepository,
    private val skinsRepository: SkinsRepository,
    private val resourcesRepository: ResourcesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(
                prefsRepository,
                skinsRepository,
                resourcesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}