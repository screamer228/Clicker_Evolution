package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.presentation.model.Stats
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getStats() : Flow<Stats>
    suspend fun updateStats(stats: Stats)
}