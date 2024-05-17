package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.PrefsRepository
import com.example.clickerevolution.data.repository.SkinsRepository
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val skinsRepository: SkinsRepository
) : ViewModel() {

    var tickValue = 1

    private val _currentSkin = MutableStateFlow(CurrentSkin())
    val currentSkin: StateFlow<CurrentSkin> = _currentSkin.asStateFlow()

    private val _currentGold = MutableStateFlow(Resources())
    val currentGold: StateFlow<Resources> = _currentGold.asStateFlow()

    init {
        getInitialSkin()
        getGoldValue()
    }

    fun incrementGold() {
        val incrementedGold = currentGold.value.gold + tickValue
        _currentGold.value = _currentGold.value.copy(gold = incrementedGold)
    }

    fun subtractGold(price: Int) {
        val reducedGold = currentGold.value.gold - price
        _currentGold.value = _currentGold.value.copy(gold = reducedGold)
    }

    private fun getGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs()
            _currentGold.value = _currentGold.value.copy(gold = goldValue.toInt())
        }
    }

    fun saveGoldValue(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(value)
        }
    }

    fun getInitialSkin() {
        viewModelScope.launch(Dispatchers.IO) {
            val skin = skinsRepository.getCurrentSkin()
            setCurrentSkin(skin)
        }
    }

    fun setCurrentSkin(skin: CurrentSkin) {
        _currentSkin.value = skin
    }
}