package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.data.room.stats.StatsDao
import com.example.clickerevolution.data.room.stats.mapper.toEntity
import com.example.clickerevolution.data.room.stats.mapper.toResources
import com.example.clickerevolution.presentation.model.Stats
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override suspend fun getResources(): Stats {
        val resourcesEntity = statsDao.getResources()
        return resourcesEntity?.toResources() ?: Stats()
    }

    override suspend fun updateResources(stats: Stats) {
        statsDao.updateResources(stats.toEntity())
    }
}