package com.example.clickerevolution.data.room.skins

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinDao {
    @Query("SELECT * FROM skins")
    fun getAllSkins(): Flow<List<SkinEntity>>

    @Query("SELECT * FROM skins WHERE isEquipped = 1")
    fun getCurrentSkin(): SkinEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkin(skin: SkinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkins(skins: List<SkinEntity>)

    @Update
    suspend fun updateSkin(skin: SkinEntity)

    @Query("UPDATE skins SET isPurchased = :isPurchased WHERE id = :skinId")
    suspend fun updateSkinPurchased(skinId: Int, isPurchased: Boolean)

    @Query("UPDATE skins SET isEquipped = :isEquipped WHERE id = :skinId")
    suspend fun updateSkinEquipped(skinId: Int, isEquipped: Boolean)

    @Query("UPDATE skins SET isEquipped = 0 WHERE isEquipped = 1")
    suspend fun unequipAllSkins()
}