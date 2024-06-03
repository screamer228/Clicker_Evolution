package com.example.clickerevolution.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.example.clickerevolution.R

data class CurrentSkin(
    @DrawableRes val imageId: Int = R.drawable.img_pig,
    @RawRes val soundId: Int = R.raw.sound_cookie_click
)
