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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
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
        viewModelScope.launch {
            upgradesRepository.getUpgradesByType(UpgradeType.CLICK_TICK)
                .flowOn(Dispatchers.IO)
                .collect { upgradesClickList ->
                    _upgradesClickList.update {
                        upgradesClickList
                    }
                }
        }
    }

    fun getUpgradesPerSecList() {
        viewModelScope.launch {
            upgradesRepository.getUpgradesByType(UpgradeType.TICK_PER_SEC)
                .flowOn(Dispatchers.IO)
                .collect { upgradesPerSecList ->
                    _upgradesPerSecList.update {
                        upgradesPerSecList
                    }
                }
        }
    }

    fun getUpgradesSpecialList() {
        viewModelScope.launch(Dispatchers.IO) {
            upgradesRepository.getUpgradesByType(UpgradeType.SPECIAL)
                .flowOn(Dispatchers.IO)
                .collect { upgradesSpecialList ->
                    _upgradesSpecialList.update {
                        upgradesSpecialList
                    }
                }
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