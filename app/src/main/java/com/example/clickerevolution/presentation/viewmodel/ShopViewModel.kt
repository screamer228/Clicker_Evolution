package com.example.clickerevolution.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository
) : ViewModel() {

//    private val _skinList = MutableStateFlow<List<Skin>>(listOf())
//    val skinList: StateFlow<List<Skin>> = _skinList.asStateFlow()

    val skinsList: StateFlow<List<Skin>> = skinsRepository.allSkins.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun purchaseSkin(skinId: Int) {
        viewModelScope.launch {
            skinsRepository.purchaseSkin(skinId)
        }
    }

    fun equipSkin(skinId: Int) {
        viewModelScope.launch {
            skinsRepository.equipSkin(skinId)
        }
    }

    fun unequipSkin(skinId: Int) {
        viewModelScope.launch {
            skinsRepository.unequipSkin(skinId)
        }
    }
}