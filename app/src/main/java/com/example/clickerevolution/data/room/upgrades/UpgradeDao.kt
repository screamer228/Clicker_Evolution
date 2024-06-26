package com.example.clickerevolution.data.room.upgrades

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.room.upgrades.entity.UpgradeEntity

@Dao
interface UpgradeDao {
    @Query("SELECT * FROM upgrades WHERE type = :type")
    suspend fun getUpgradesByType(type: UpgradeType): List<UpgradeEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpgrades(upgrades: List<UpgradeEntity>)
    @Query("UPDATE upgrades SET level = level + 1, price = price * 1.2 WHERE id = :upgradeId")
    suspend fun upgradeLevelAndPrice(upgradeId: Int)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpgrade(upgrade: UpgradeEntity)
    @Update
    suspend fun updateUpgrade(upgrade: UpgradeEntity)
}