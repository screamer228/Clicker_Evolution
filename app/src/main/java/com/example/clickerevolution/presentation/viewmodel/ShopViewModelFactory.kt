package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.skins.SkinsRepository

class ShopViewModelFactory(
    private val skinsRepository: SkinsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShopViewModel(
                skinsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}