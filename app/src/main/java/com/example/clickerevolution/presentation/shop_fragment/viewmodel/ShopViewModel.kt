package com.example.clickerevolution.presentation.shop_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository
) : ViewModel() {

    private val _skinsList = MutableStateFlow<List<Skin>>(listOf())
    val skinsList: StateFlow<List<Skin>> = _skinsList.asStateFlow()

//    val skinsList: StateFlow<List<Skin>> = skinsRepository.allSkins.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        emptyList()
//    )

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            // Заполняем базу данных, если она пуста
//            if (skinsRepository.getAllSkins().isEmpty()) {
//                skinsRepository.prepopulateSkinsDatabase()
//            }
//            getSkinsList()
//        }
//    }

    init {
        getSkinsList()
    }

    private fun getSkinsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _skinsList.value = skinsRepository.getAllSkins()
        }
    }

    fun purchaseSkin(skinId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            skinsRepository.purchaseSkin(skinId)
            getSkinsList()
        }
    }

    fun equipSkin(skinId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            skinsRepository.equipSkin(skinId)
            getSkinsList()
        }
    }

    fun unequipSkin(skinId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            skinsRepository.unequipSkin(skinId)
            getSkinsList()
        }
    }
}