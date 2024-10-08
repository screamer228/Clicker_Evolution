package com.example.clickerevolution.data.room.stats.mapper

import com.example.clickerevolution.data.room.stats.entity.StatsEntity
import com.example.clickerevolution.presentation.model.Stats

fun StatsEntity.toStats(): Stats {
    return Stats(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue,
        diamondsTickByFullBar = diamondsTickByFullBar,
        diamondProgressBar = diamondProgressBar,
        goldOfflineMultiplier = goldOfflineMultiplier
    )
}

fun Stats.toEntity(): StatsEntity {
    return StatsEntity(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue,
        diamondsTickByFullBar = diamondsTickByFullBar,
        diamondProgressBar = diamondProgressBar,
        goldOfflineMultiplier = goldOfflineMultiplier
    )
}