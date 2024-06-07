package com.example.clickerevolution.data.room.stats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.clickerevolution.data.room.stats.entity.StatsEntity

@Dao
interface StatsDao {
    @Query("SELECT * FROM stats WHERE id = 0")
    suspend fun getStats(): StatsEntity?

    @Insert
    suspend fun insertStats(resources: StatsEntity)

    @Update
    suspend fun updateStats(resources: StatsEntity)
}