package com.example.clickerevolution.presentation.upgrades_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesViewModel @Inject constructor(
    private val upgradesRepository: UpgradesRepository
) : ViewModel() {

    private val _upgradesClickList = MutableStateFlow<List<Upgrade>>(listOf())
    val upgradesClickList: StateFlow<List<Upgrade>> = _upgradesClickList.asStateFlow()

    private val _upgradesPerSecList = MutableStateFlow<List<Upgrade>>(listOf())
    val upgradesPerSecList: StateFlow<List<Upgrade>> = _upgradesPerSecList.asStateFlow()

    init {
        Log.d("upgrades check", "init UpgradesViewModel")
        getUpgradesClickList()
        getUpgradesPerSecList()
        Log.d(
            "upgrades check",
            "after init: ${_upgradesClickList.value.size}; ${_upgradesPerSecList.value.size}"
        )
    }

    fun getUpgradesClickList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesClickList.value = upgradesRepository.getUpgradesByType(UpgradeType.CLICK_TICK)
        }
        Log.d(
            "upgrades check",
            "clickTick: ${_upgradesClickList.value.size}"
        )
    }

    fun getUpgradesPerSecList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesPerSecList.value =
                upgradesRepository.getUpgradesByType(UpgradeType.TICK_PER_SEC)
        }
        Log.d(
            "upgrades check",
            "tickPerSec: ${_upgradesPerSecList.value.size}"
        )
    }

    fun upgradeLevelAndPrice(upgradeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            upgradesRepository.upgradeLevelAndPrice(upgradeId)
            getUpgradesClickList()
            getUpgradesPerSecList()
        }
    }
}