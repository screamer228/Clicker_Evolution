package com.example.clickerevolution.presentation.upgrades_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.Dispatchers
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

    private val _upgradesSpecialList = MutableStateFlow<List<Upgrade>>(listOf())
    val upgradesSpecialList: StateFlow<List<Upgrade>> = _upgradesSpecialList.asStateFlow()

    init {
        getUpgradesClickList()
        getUpgradesPerSecList()
        getUpgradesSpecialList()
    }

    fun getUpgradesClickList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesClickList.value = upgradesRepository.getUpgradesByType(UpgradeType.CLICK_TICK)
        }
    }

    fun getUpgradesPerSecList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesPerSecList.value =
                upgradesRepository.getUpgradesByType(UpgradeType.TICK_PER_SEC)
        }
    }

    fun getUpgradesSpecialList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesSpecialList.value =
                upgradesRepository.getUpgradesByType(UpgradeType.SPECIAL)
        }
    }

    fun upgradeLevelAndPrice(upgradeId: Int, upgradeType: UpgradeType) {
        viewModelScope.launch(Dispatchers.IO) {
            upgradesRepository.upgradeLevelAndPrice(upgradeId)

            when (upgradeType) {
                UpgradeType.CLICK_TICK -> getUpgradesClickList()

                UpgradeType.TICK_PER_SEC -> getUpgradesPerSecList()

                UpgradeType.SPECIAL -> getUpgradesSpecialList()
            }
        }
    }
}