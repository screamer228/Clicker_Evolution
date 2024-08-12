package com.example.clickerevolution.data.repository.skins

import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.Flow

interface SkinsRepository {
    fun getAllSkins(): Flow<List<Skin>>
    fun getCurrentSkin(): Flow<CurrentSkin>
    suspend fun purchaseSkin(skinId: Int)
    suspend fun equipSkin(skinId: Int)
    suspend fun unequipSkin(skinId: Int)
    suspend fun insertSkin(skin: Skin)
}