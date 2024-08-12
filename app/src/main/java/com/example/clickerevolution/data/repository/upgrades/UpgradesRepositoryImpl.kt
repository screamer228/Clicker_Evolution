package com.example.clickerevolution.data.repository.upgrades

import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.room.upgrades.UpgradeDao
import com.example.clickerevolution.data.room.upgrades.mapper.toUpgrade
import com.example.clickerevolution.presentation.model.Upgrade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpgradesRepositoryImpl @Inject constructor(
    private val upgradeDao: UpgradeDao
) : UpgradesRepository {

    override fun getUpgradesByType(type: UpgradeType): Flow<List<Upgrade>> = flow {
        emit(
            upgradeDao.getUpgradesByType(type).map { it.toUpgrade() }
        )
    }

    override suspend fun upgradeLevelAndPrice(upgradeId: Int) {
        upgradeDao.upgradeLevelAndPrice(upgradeId)
    }
}