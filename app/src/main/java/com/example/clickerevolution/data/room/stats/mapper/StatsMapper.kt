package com.example.clickerevolution.data.room.stats.mapper

import com.example.clickerevolution.data.room.stats.entity.StatsEntity
import com.example.clickerevolution.presentation.model.Stats

fun StatsEntity.toResources(): Stats {
    return Stats(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue
    )
}

fun Stats.toEntity(): StatsEntity {
    return StatsEntity(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue,
        diamondProgressBar = diamondProgressBar
    )
}