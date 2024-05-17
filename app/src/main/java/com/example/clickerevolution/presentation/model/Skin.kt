package com.example.clickerevolution.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Skin(
    val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    @RawRes val soundId: Int,
    val price: Int,
    val isPurchased: Boolean = false,
    val isEquipped: Boolean = false
)