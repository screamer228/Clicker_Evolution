package com.example.clickerevolution.data.room.resources.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resources")
data class ResourcesEntity(
    @PrimaryKey val id: Int = 0,
    val goldClickTickValue: Int,
    val goldTickPerSecValue: Int
)
