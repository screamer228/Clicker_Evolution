package com.example.clickerevolution.data.repository.upgrades

import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.flow.Flow

interface UpgradesRepository {
    fun getUpgradesByType(type: UpgradeType): Flow<List<Upgrade>>
    suspend fun upgradeLevelAndPrice(upgradeId: Int)
}