package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.data.room.stats.StatsDao
import com.example.clickerevolution.data.room.stats.mapper.toEntity
import com.example.clickerevolution.data.room.stats.mapper.toStats
import com.example.clickerevolution.presentation.model.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override fun getStats(): Flow<Stats> = flow {
        statsDao.getStats()?.toStats()?.let { emit(it) }
    }

    override suspend fun updateStats(stats: Stats) {
        statsDao.updateStats(stats.toEntity())
    }
}