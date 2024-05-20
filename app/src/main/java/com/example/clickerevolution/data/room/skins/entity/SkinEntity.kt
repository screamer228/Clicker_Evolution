package com.example.clickerevolution.data.room.skins.entity

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skins")
data class SkinEntity(
    @PrimaryKey val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    @RawRes val soundId: Int,
    val price: Int,
    val isPurchased: Boolean = false,
    val isEquipped: Boolean = false
)