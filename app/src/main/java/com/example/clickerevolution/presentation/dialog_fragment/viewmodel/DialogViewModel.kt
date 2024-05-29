package com.example.clickerevolution.presentation.dialog_fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
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

class DialogViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _goldEarned = MutableStateFlow<List<Skin>>(listOf())
    val goldEarned: StateFlow<List<Skin>> = _goldEarned.asStateFlow()

//    init {
//        getGoldEarnedWhileOffline()
//    }
//
//    private fun getGoldEarnedWhileOffline() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _goldEarned.value = prefsRepository.
//        }
//    }
//
//    private fun getSkinsList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _skinsList.value = skinsRepository.getAllSkins()
//        }
//    }
//
//    fun purchaseSkin(skinId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            skinsRepository.purchaseSkin(skinId)
//            getSkinsList()
//        }
//    }
//
//    fun equipSkin(skinId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            skinsRepository.equipSkin(skinId)
//            getSkinsList()
//        }
//    }
//
//    fun unequipSkin(skinId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            skinsRepository.unequipSkin(skinId)
//            getSkinsList()
//        }
//    }
}