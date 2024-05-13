package com.example.clickerevolution.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.PrefsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _resources = MutableStateFlow(0)
    val resourcesFlow: StateFlow<Int> = _resources

    init {
        getGoldValue()
    }

    fun incrementResources() {
        _resources.value += 1
    }

    private fun getGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs()
            _resources.value = goldValue.toInt()
        }
    }

    fun saveGoldValue(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(value)
        }
    }
}