package com.example.clickerevolution.data.repository.upgrades

import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.room.upgrades.UpgradeDao
import com.example.clickerevolution.data.room.upgrades.mapper.toUpgrade
import com.example.clickerevolution.presentation.model.Upgrade
import javax.inject.Inject

class UpgradesRepositoryImpl @Inject constructor(
    private val upgradeDao: UpgradeDao
) : UpgradesRepository {

    override suspend fun getUpgradesByType(type: UpgradeType): List<Upgrade> {
        return upgradeDao.getUpgradesByType(type).map { it.toUpgrade() }
    }

    override suspend fun upgradeLevelAndPrice(upgradeId: Int) {
        upgradeDao.upgradeLevelAndPrice(upgradeId)
    }
}