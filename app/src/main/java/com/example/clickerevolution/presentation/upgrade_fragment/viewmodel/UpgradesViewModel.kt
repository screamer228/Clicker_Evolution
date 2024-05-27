package com.example.clickerevolution.presentation.upgrade_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesViewModel @Inject constructor(
    private val upgradesRepository: UpgradesRepository
) : ViewModel() {

    private val _upgradesList = MutableStateFlow<List<Upgrade>>(listOf())
    val upgradesList: StateFlow<List<Upgrade>> = _upgradesList.asStateFlow()

    init {
        getUpgradesList()
    }

    private fun getUpgradesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _upgradesList.value = upgradesRepository.getUpgradesByType(UpgradeType.CLICK_TICK)
        }
    }

    fun upgradeLevelAndPrice(upgradeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            upgradesRepository.upgradeLevelAndPrice(upgradeId)
            getUpgradesList()
        }
    }
}