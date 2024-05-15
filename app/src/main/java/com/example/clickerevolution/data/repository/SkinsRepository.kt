package com.example.clickerevolution.data.repository

import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.Flow

interface SkinsRepository {

    val allSkins: Flow<List<Skin>>

    suspend fun purchaseSkin(skinId: Int)
    suspend fun equipSkin(skinId: Int)
    suspend fun unequipSkin(skinId: Int)
    suspend fun insertSkin(skin: Skin)
}