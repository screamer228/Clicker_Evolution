package com.example.clickerevolution.data.room.resources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.clickerevolution.data.room.resources.entity.ResourcesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourcesDao {
    @Query("SELECT * FROM resources WHERE id = 0")
    suspend fun getResources(): ResourcesEntity?

    @Insert
    suspend fun insertResources(resources: ResourcesEntity)

    @Update
    suspend fun updateResources(resources: ResourcesEntity)
}