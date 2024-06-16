package com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.data.repository.prefs.PrefsRepository

class DailyRewardsViewModelFactory(
    private val prefsRepository: PrefsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyRewardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyRewardsViewModel(
                prefsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}