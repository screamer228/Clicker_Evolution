package com.example.clickerevolution.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.example.clickerevolution.common.Rarity

data class Skin(
    val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    @RawRes val soundId: Int,
    val price: Int,
    val rarity: Rarity,
    val isPurchased: Boolean = false,
    val isEquipped: Boolean = false
)