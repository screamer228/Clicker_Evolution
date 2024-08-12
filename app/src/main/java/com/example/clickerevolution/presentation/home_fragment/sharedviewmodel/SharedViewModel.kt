package com.example.clickerevolution.presentation.home_fragment.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.Price
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Resources
import com.example.clickerevolution.presentation.model.Stats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
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
        val incrementedDiamonds =
            _currentResources.value.diamonds + _currentStats.value.diamondsTickByFullBar
        setDiamondsValue(incrementedDiamonds)
        resetDiamondProgressBar()
    }

    private fun resetDiamondProgressBar() {
        _currentStats.update { stats ->
            stats.copy(
                diamondProgressBar = 0
            )
        }
    }

    fun calculateGoldForOfflineTime(): Int {
        val lastExitTime = prefsRepository.getLastExitTime()
        val currentTime = System.currentTimeMillis()
        var elapsedTime = (currentTime - lastExitTime) / 1000

        if (elapsedTime > MAX_OFFLINE_PRODUCTION_TIME) {
            elapsedTime = MAX_OFFLINE_PRODUCTION_TIME.toLong()
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

    private fun incrementGoldByClick() {
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
        _currentResources.update {
            it.copy(
                gold = value
            )
        }
    }

    private fun setDiamondsValue(value: Int) {
        _currentResources.update {
            it.copy(
                diamonds = value
            )
        }
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
        viewModelScope.launch {
            skinsRepository.getCurrentSkin()
                .flowOn(Dispatchers.IO)
                .collect {
                    setCurrentSkin(it)
                }
        }
    }

    fun setCurrentSkin(skin: CurrentSkin) {
        _currentSkin.update {
            skin
        }
    }

    private fun getInitialStats() {
        viewModelScope.launch {
            statsRepository.getStats()
                .flowOn(Dispatchers.IO)
                .collect { stats ->
                    _currentStats.update {
                        stats
                    }
                }
        }
    }

    fun setCurrentClickTick(plusTickValue: Int) {
        _currentStats.update { stats ->
            stats.copy(
                goldClickTickValue = stats.goldClickTickValue + plusTickValue
            )
        }
    }

    fun setCurrentTickPerSec(plusTickValue: Int) {
        _currentStats.update { stats ->
            stats.copy(
                goldTickPerSecValue = stats.goldTickPerSecValue + plusTickValue
            )
        }
    }

    fun setCurrentGoldOfflineMultiplier(plusPercent: Int) {
        _currentStats.update { stats ->
            stats.copy(
                goldOfflineMultiplier = (stats.goldTickPerSecValue + plusPercent).toFloat() / 100
            )
        }
    }

    fun setCurrentDiamondsTick(plusTickValue: Int) {
        _currentStats.update { stats ->
            stats.copy(
                diamondsTickByFullBar = stats.diamondsTickByFullBar + plusTickValue
            )
        }
    }

    fun saveStats() {
        viewModelScope.launch(Dispatchers.IO) {
            statsRepository.updateStats(currentStats.value)
        }
    }
}

private const val MAX_OFFLINE_PRODUCTION_TIME = 14400