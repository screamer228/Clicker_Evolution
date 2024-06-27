package com.example.clickerevolution.presentation.home_fragment.sharedviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.Price
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Resources
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

    private val _currentResources = MutableStateFlow(Resources())
    val currentResources: StateFlow<Resources> = _currentResources.asStateFlow()

    private val _currentStats = MutableStateFlow(Stats())
    val currentStats: StateFlow<Stats> = _currentStats.asStateFlow()

    var previousDiamonds = 0

    init {
        getInitialSkin()
        getInitialResources()
        getInitialStats()
        startPassiveGoldIncrement()
        previousDiamonds = _currentResources.value.diamonds
//        calculateGoldForOfflineTime()
    }

    fun claimReward(price: Price) {
        when (price.type) {
            Currency.GOLD -> {
                setGoldValue(_currentResources.value.gold + price.value)
            }

            Currency.DIAMOND -> {
                setDiamondsValue(_currentResources.value.diamonds + price.value)
            }
        }
    }

    fun onButtonClick() {
        incrementGoldByClick()
        val newCount = _currentStats.value.diamondProgressBar + 1
        _currentStats.value = _currentStats.value.copy(diamondProgressBar = newCount)
        if (newCount >= 200) {
            onMaxClicksReached()
        }
    }

    private fun onMaxClicksReached() {
        val incrementedDiamonds = _currentResources.value.diamonds + 1
        setDiamondsValue(incrementedDiamonds)
        _currentStats.value = _currentStats.value.copy(diamondProgressBar = 0)
    }

    fun calculateGoldForOfflineTime(): Int {
        val lastExitTime = prefsRepository.getLastExitTime()
        val currentTime = System.currentTimeMillis()
        var elapsedTime = (currentTime - lastExitTime) / 1000

        if (elapsedTime > 7200) {
            elapsedTime = 7200
        }

        val goldIncrement =
            elapsedTime * currentStats.value.goldTickPerSecValue * currentStats.value.goldOfflineMultiplier
        return goldIncrement.toInt()
    }

    fun incrementGoldEarnedWhileOffline(goldValue: Int) {
        setGoldValue(currentResources.value.gold + goldValue)
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
        val incrementedGold = currentResources.value.gold + currentStats.value.goldTickPerSecValue
        setGoldValue(incrementedGold)
    }

    fun incrementGoldByClick() {
        val incrementedGold = currentResources.value.gold + currentStats.value.goldClickTickValue
        setGoldValue(incrementedGold)
    }

    fun subtractGold(price: Int) {
        val reducedGold = currentResources.value.gold - price
        setGoldValue(reducedGold)
    }

    fun subtractDiamonds(price: Int) {
        val reducedDiamonds = currentResources.value.diamonds - price
        setDiamondsValue(reducedDiamonds)
    }

    private fun getInitialResources() {
        viewModelScope.launch(Dispatchers.IO) {
            val goldValue = prefsRepository.getGoldValueFromPrefs().toIntOrNull() ?: 0
            val diamondValue = prefsRepository.getDiamondValueFromPrefs().toIntOrNull() ?: 0
            setGoldValue(goldValue)
            setDiamondsValue(diamondValue)
        }
    }

    private fun setGoldValue(value: Int) {
        _currentResources.value = _currentResources.value.copy(gold = value)
    }

    private fun setDiamondsValue(value: Int) {
        _currentResources.value = _currentResources.value.copy(diamonds = value)
    }

    fun synchronizeDiamonds() {
        previousDiamonds = _currentResources.value.diamonds
    }

    fun saveResources() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveGoldValueInPrefs(currentResources.value.gold.toString())
            prefsRepository.saveDiamondValueInPrefs(currentResources.value.diamonds.toString())
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

    private fun getInitialStats() {
        viewModelScope.launch(Dispatchers.IO) {
            val stats = statsRepository.getStats()
            _currentStats.value = stats
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

    fun setCurrentOfflineGoldMultiplier(plusPercent: Int) {
        val incrementedMultiplier = _currentStats.value.goldTickPerSecValue + plusPercent
        _currentStats.value =
            _currentStats.value.copy(goldOfflineMultiplier = incrementedMultiplier.toFloat() / 100)
    }

    fun saveStats() {
        viewModelScope.launch(Dispatchers.IO) {
            statsRepository.updateStats(currentStats.value)
        }
    }
}