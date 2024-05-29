package com.example.clickerevolution.presentation.sharedviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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
        startPassiveGoldIncrement()
    }

    private fun calculateGoldForOfflineTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val lastExitTime = prefsRepository.getLastExitTime()
            if (lastExitTime > 0) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = (currentTime - lastExitTime) / 1000 // в секундах
                val goldIncrement = elapsedTime * currentResources.value.goldTickPerSecValue
//                setGoldValue(currentGold.value + goldIncrement.toInt())
            }
        }
    }

    fun saveLastExitTime() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveLastExitTime(System.currentTimeMillis())
        }
    }

    private fun startPassiveGoldIncrement() {
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(1000L) // 1 секунда
                }
            }.collect {
                incrementGoldPerSecond()
            }
        }
    }

    private fun incrementGoldPerSecond() {
        val incrementedGold = currentGold.value + currentResources.value.goldTickPerSecValue
        setGoldValue(incrementedGold)
    }

    fun incrementGoldByClick() {
        val incrementedGold = currentGold.value + currentResources.value.goldClickTickValue
        setGoldValue(incrementedGold)
    }

    fun subtractGold(price: Int) {
        val reducedGold = currentGold.value - price
        setGoldValue(reducedGold)
    }

    private fun getInitialGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs().toIntOrNull() ?: 0
            Log.d("sharedPrefs check", "prefsRepository instance in viewModel: $prefsRepository")
            Log.d("sharedPrefs check", "in viewModel: $goldValue")
            setGoldValue(goldValue)
        }
    }

    private fun setGoldValue(value: Int) {
        Log.d("sharedPrefs check", "inViewModel when set: $value")
        _currentGold.value = value
    }

    fun saveGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(currentGold.value.toString())
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

    private fun getInitialResources() {
        viewModelScope.launch(Dispatchers.IO) {
            val resources = resourcesRepository.getResources()
            _currentResources.value = resources
        }
    }

    fun setCurrentClickTick(plusTickValue: Int) {
        val incrementedGold = _currentResources.value.goldClickTickValue + plusTickValue
        _currentResources.value = _currentResources.value.copy(goldClickTickValue = incrementedGold)
    }

    fun saveResources() {
        viewModelScope.launch(Dispatchers.IO) {
            resourcesRepository.updateResources(currentResources.value)
        }
    }
}