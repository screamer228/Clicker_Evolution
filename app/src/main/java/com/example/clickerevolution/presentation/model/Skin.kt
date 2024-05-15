package com.example.clickerevolution.presentation.model

import androidx.annotation.DrawableRes

data class Skin(
    val title: String,
    @DrawableRes val imageId: Int,
    val price: Int
)
