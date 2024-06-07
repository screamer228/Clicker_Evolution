package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.data.room.stats.StatsDao
import com.example.clickerevolution.data.room.stats.mapper.toEntity
import com.example.clickerevolution.data.room.stats.mapper.toStats
import com.example.clickerevolution.presentation.model.Stats
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override suspend fun getStats(): Stats {
        val statsEntity = statsDao.getStats()
        return statsEntity?.toStats() ?: Stats()
    }

    override suspend fun updateStats(stats: Stats) {
        statsDao.updateStats(stats.toEntity())
    }
}