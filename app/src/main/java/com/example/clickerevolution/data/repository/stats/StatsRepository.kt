package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.presentation.model.Stats

interface StatsRepository {
    suspend fun getStats() : Stats
    suspend fun updateStats(stats: Stats)
}