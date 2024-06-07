package com.example.clickerevolution.data.repository.stats

import com.example.clickerevolution.presentation.model.Stats

interface StatsRepository {
    suspend fun getResources() : Stats
    suspend fun updateResources(stats: Stats)
}