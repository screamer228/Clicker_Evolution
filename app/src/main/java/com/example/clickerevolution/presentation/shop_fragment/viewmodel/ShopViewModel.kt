package com.example.clickerevolution.presentation.shop_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val skinsRepository: SkinsRepository
) : ViewModel() {

    private val _skinsList = MutableStateFlow<List<Skin>>(listOf())
    val skinsList: StateFlow<List<Skin>> = _skinsList.asStateFlow()

    init {
        getSkinsList()
    }

    private fun getSkinsList() {
        viewModelScope.launch {
            skinsRepository.getAllSkins()
                .flowOn(Dispatchers.IO)
                .collect { skinsList ->
                    _skinsList.update {
                        skinsList
                    }
                }
        }
        Log.d("flow check", "${skinsList.value.size}")
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