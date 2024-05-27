package com.example.clickerevolution.presentation.upgrade_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository

class UpgradesViewModelFactory(
    private val upgradesRepository: UpgradesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpgradesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpgradesViewModel(
                upgradesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}