package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.PrefsRepository
import com.example.clickerevolution.presentation.model.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    var tickValue = 1

    private val _resources = MutableStateFlow(Resources())
    val resourcesFlow: StateFlow<Resources> = _resources.asStateFlow()

    init {
        getGoldValue()
    }

    fun incrementResources() {
        val incrementedGold = resourcesFlow.value.gold + tickValue
        _resources.value = _resources.value.copy(gold = incrementedGold)
    }

    private fun getGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs()
            _resources.value = _resources.value.copy(gold = goldValue.toInt())
        }
    }

    fun saveGoldValue(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(value)
        }
    }
}