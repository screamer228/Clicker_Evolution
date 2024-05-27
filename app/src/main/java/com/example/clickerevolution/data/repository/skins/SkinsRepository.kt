package com.example.clickerevolution.data.repository.skins

import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.Flow

interface SkinsRepository {
    suspend fun getAllSkins(): List<Skin>
    suspend fun prepopulateSkinsDatabase()
    suspend fun getCurrentSkin(): CurrentSkin
    suspend fun purchaseSkin(skinId: Int)
    suspend fun equipSkin(skinId: Int)
    suspend fun unequipSkin(skinId: Int)
    suspend fun insertSkin(skin: Skin)
}