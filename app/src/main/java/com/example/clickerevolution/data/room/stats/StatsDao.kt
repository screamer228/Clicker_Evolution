package com.example.clickerevolution.data.room.stats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.clickerevolution.data.room.stats.entity.StatsEntity

@Dao
interface StatsDao {
    @Query("SELECT * FROM resources WHERE id = 0")
    suspend fun getResources(): StatsEntity?

    @Insert
    suspend fun insertResources(resources: StatsEntity)

    @Update
    suspend fun updateResources(resources: StatsEntity)
}