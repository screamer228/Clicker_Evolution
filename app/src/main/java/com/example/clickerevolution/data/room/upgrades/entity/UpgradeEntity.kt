package com.example.clickerevolution.data.room.upgrades.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.clickerevolution.common.UpgradeType

@Entity(tableName = "upgrades")
data class UpgradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val power: Int,
    val level: Int = 0,
    val price: Int,
    val type: UpgradeType
)