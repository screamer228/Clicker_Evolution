package com.example.clickerevolution.presentation.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Stats
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
    private val statsRepository: StatsRepository
) : ViewModel() {

    private val _currentSkin = MutableStateFlow(CurrentSkin())
    val currentSkin: StateFlow<CurrentSkin> = _currentSkin.asStateFlow()

    private val _currentGold = MutableStateFlow(0)
    val currentGold: StateFlow<Int> = _currentGold.asStateFlow()

    private val _currentStats = MutableStateFlow(Stats())
    val currentStats: StateFlow<Stats> = _currentStats.asStateFlow()

    init {
        getInitialSkin()
        getInitialGoldValue()
        getInitialResources()
        startPassiveGoldIncrement()
//        calculateGoldForOfflineTime()
    }

//    fun onButtonClick() {
//        val newCount = _currentResources.value.diamondProgressBar + 1
//        _currentResources.value = _currentResources.value.copy(diamondProgressBar = )
//        if (newCount >= 200) {
//            // Выполняем необходимое действие
//            onMaxClicksReached()
//        }
//    }

    private fun onMaxClicksReached() {
        // Выполняем действие по достижению 200 кликов
        // Например, показываем сообщение
        // Сбрасываем счетчик кликов
        _currentStats.value = _currentStats.value.copy(diamondProgressBar = 0)
    }

    fun calculateGoldForOfflineTime(): Int {
        val lastExitTime = prefsRepository.getLastExitTime()
        val currentTime = System.currentTimeMillis()
        val elapsedTime = (currentTime - lastExitTime) / 1000 // в секундах
        val goldIncrement = elapsedTime * currentStats.value.goldTickPerSecValue
        return goldIncrement.toInt()
//        setGoldValue(currentGold.value + goldIncrement.toInt())
    }

    fun incrementGoldEarnedWhileOffline(goldValue: Int) {
        setGoldValue(currentGold.value + goldValue)
    }

//    private fun getLastExitTime(): Long {
//        return prefsRepository.getLastExitTime()
//    }

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
        val incrementedGold = currentGold.value + currentStats.value.goldTickPerSecValue
        setGoldValue(incrementedGold)
    }

    fun incrementGoldByClick() {
        val incrementedGold = currentGold.value + currentStats.value.goldClickTickValue
        setGoldValue(incrementedGold)
    }

    fun subtractGold(price: Int) {
        val reducedGold = currentGold.value - price
        setGoldValue(reducedGold)
    }

    private fun getInitialGoldValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs().toIntOrNull() ?: 0
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
            val resources = statsRepository.getResources()
            _currentStats.value = resources
        }
    }

    fun setCurrentClickTick(plusTickValue: Int) {
        val incrementedTick = _currentStats.value.goldClickTickValue + plusTickValue
        _currentStats.value = _currentStats.value.copy(goldClickTickValue = incrementedTick)
    }

    fun setCurrentTickPerSec(plusTickValue: Int) {
        val incrementedTick = _currentStats.value.goldTickPerSecValue + plusTickValue
        _currentStats.value = _currentStats.value.copy(goldTickPerSecValue = incrementedTick)
    }

    fun saveResources() {
        viewModelScope.launch(Dispatchers.IO) {
            statsRepository.updateResources(currentStats.value)
        }
    }
}