package com.example.clickerevolution.data.room.stats.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats")
data class StatsEntity(
    @PrimaryKey val id: Int = 0,
    val goldClickTickValue: Int,
    val goldTickPerSecValue: Int,
    val diamondsTickByFullBar: Int,
    val diamondProgressBar: Int,
    val goldOfflineMultiplier: Float
)
