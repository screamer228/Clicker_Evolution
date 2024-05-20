package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
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
    private val skinsRepository: SkinsRepository,
    private val resourcesRepository: ResourcesRepository
) : ViewModel() {

    private val _currentSkin = MutableStateFlow(CurrentSkin())
    val currentSkin: StateFlow<CurrentSkin> = _currentSkin.asStateFlow()

    private val _currentGold = MutableStateFlow(0)
    val currentGold: StateFlow<Int> = _currentGold.asStateFlow()

    private val _currentResources = MutableStateFlow(Resources())
    val currentResources: StateFlow<Resources> = _currentResources.asStateFlow()

    init {
        getInitialSkin()
        getInitialGoldValue()
        getInitialResources()
    }

    fun incrementGold() {
        val incrementedGold = currentGold.value + currentResources.value.goldClickTickValue
        setGoldValue(incrementedGold)
    }

    fun subtractGold(price: Int) {
        val reducedGold = currentGold.value - price
        setGoldValue(reducedGold)
    }

    private fun getInitialGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs().toInt()
            setGoldValue(goldValue)
        }
    }

    private fun setGoldValue(value: Int) {
        _currentGold.value = value
    }

    fun saveGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(currentGold.value.toString())
        }
    }

    private fun getInitialSkin() {
        viewModelScope.launch(Dispatchers.IO) {
            val skin = skinsRepository.getCurrentSkin()
            setCurrentSkin(skin)
        }
    }

    fun setCurrentSkin(skin: CurrentSkin) {
        _currentSkin.value = skin
    }

    private fun getInitialResources() {
        viewModelScope.launch(Dispatchers.IO) {
            val resources = resourcesRepository.getResources()
            _currentResources.value = resources
        }
    }
}