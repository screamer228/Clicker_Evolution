package com.example.clickerevolution.presentation.upgradedetail_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.Price
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

class UpgradeDetailViewModel @Inject constructor(
    private val upgradesRepository: UpgradesRepository
) : ViewModel() {

    private val _detailUpgrade = MutableStateFlow<Upgrade>(
        Upgrade(
            id = 0,
            title = "",
            description = "",
            power = 0,
            level = 0,
            price = Price(
                type = Currency.DIAMOND,
                value = 0
            ),
            type = UpgradeType.SPECIAL,
            isEnabled = true
        )
    )
    val detailUpgrade : StateFlow<Upgrade> = _detailUpgrade.asStateFlow()

//    init {
//        getDetailUpgrade()
//    }

    fun getDetailUpgrade(upgradeId: Int) {
        viewModelScope.launch {
            upgradesRepository.getDetailUpgradeById(upgradeId)
                .flowOn(Dispatchers.IO)
                .collect { detailUpgrade ->
                    _detailUpgrade.update {
                        detailUpgrade
                    }
                }
        }
        Log.d("special check", "$upgradeId, ${detailUpgrade.value}")
    }
}