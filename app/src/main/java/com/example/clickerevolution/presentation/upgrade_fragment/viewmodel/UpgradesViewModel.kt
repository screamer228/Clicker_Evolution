package com.example.clickerevolution.presentation.upgrade_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.Skin
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpgradesViewModel @Inject constructor(
//    private val upgradesRepository: UpgradesRepository
) : ViewModel() {

    private val _upgradesList = MutableStateFlow<List<Upgrade>>(listOf())
    val upgradesList: StateFlow<List<Upgrade>> = _upgradesList.asStateFlow()

    init {
        getUpgradesList()
    }

    private fun getUpgradesList() {
        val list = listOf(
            Upgrade(1, "Название 1", 1, 1, 500),
            Upgrade(2, "Название 2", 3, 1, 1000),
            Upgrade(3, "Название 3", 5, 1, 3000),
            Upgrade(4, "Название 4", 10, 1, 6000),
            Upgrade(5, "Название 5", 25, 1, 12000)
        )
        _upgradesList.value = list
    }

//    val skinsList: StateFlow<List<Skin>> = skinsRepository.allSkins.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        emptyList()
//    )
//
//    fun purchaseSkin(skinId: Int) {
//        viewModelScope.launch {
//            skinsRepository.purchaseSkin(skinId)
//        }
//    }
//
//    fun equipSkin(skinId: Int) {
//        viewModelScope.launch {
//            skinsRepository.equipSkin(skinId)
//        }
//    }
//
//    fun unequipSkin(skinId: Int) {
//        viewModelScope.launch {
//            skinsRepository.unequipSkin(skinId)
//        }
//    }
}