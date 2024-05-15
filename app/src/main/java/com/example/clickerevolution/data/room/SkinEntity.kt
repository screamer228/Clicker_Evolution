package com.example.clickerevolution.data.room

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skins")
data class SkinEntity(
    @PrimaryKey val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    val price: Int,
    val isPurchased: Boolean = false,
    val isEquipped: Boolean = false
)