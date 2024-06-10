package com.example.clickerevolution.data.room.skins.entity

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.clickerevolution.common.CurrencyType
import com.example.clickerevolution.common.Price
import com.example.clickerevolution.common.Rarity

@Entity(tableName = "skins")
data class SkinEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    @DrawableRes val imageId: Int,
    @RawRes val soundId: Int,
    val priceType: CurrencyType,
    val priceValue: Int,
    val rarity: Rarity,
    val isPurchased: Boolean = false,
    val isEquipped: Boolean = false,
)