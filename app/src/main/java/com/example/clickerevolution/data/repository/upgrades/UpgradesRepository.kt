package com.example.clickerevolution.data.repository.upgrades

import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.presentation.model.Upgrade

interface UpgradesRepository {
    suspend fun getUpgradesByType(type: UpgradeType): List<Upgrade>
    suspend fun upgradeLevelAndPrice(upgradeId: Int)
}