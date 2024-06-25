package com.example.clickerevolution.presentation.home_fragment.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository

class SharedViewModelFactory(
    private val prefsRepository: PrefsRepository,
    private val skinsRepository: SkinsRepository,
    private val statsRepository: StatsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(
                prefsRepository,
                skinsRepository,
                statsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}