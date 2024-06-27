package com.example.clickerevolution.presentation.model

import com.example.clickerevolution.common.Price
import com.example.clickerevolution.common.UpgradeType

data class Upgrade(
    val id: Int,
    val title: String,
    val power: Int,
    val level: Int,
    val price: Price,
    val type: UpgradeType,
    val isEnabled: Boolean = false
)