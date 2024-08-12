package com.example.clickerevolution.presentation.upgradedetail_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository

class UpgradeDetailViewModelFactory(
    private val upgradesRepository: UpgradesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpgradeDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpgradeDetailViewModel(
                upgradesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}