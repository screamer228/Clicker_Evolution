package com.example.clickerevolution.data.repository

import com.example.clickerevolution.data.mapper.toCurrentSkin
import com.example.clickerevolution.data.mapper.toEntity
import com.example.clickerevolution.data.mapper.toSkin
import com.example.clickerevolution.data.room.SkinDao
import com.example.clickerevolution.data.room.SkinEntity
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkinsRepositoryImpl @Inject constructor(
    private val skinDao: SkinDao
) : SkinsRepository {

    override val allSkins: Flow<List<Skin>> = skinDao.getAllSkins().map { entities ->
        entities.map { it.toSkin() }
    }

    override suspend fun getCurrentSkin(): CurrentSkin {
        val skinEntity = skinDao.getCurrentSkin()
        return skinEntity?.toCurrentSkin() ?: CurrentSkin()
    }

    override suspend fun purchaseSkin(skinId: Int) {
        skinDao.updateSkinPurchased(skinId, true)
    }

    override suspend fun equipSkin(skinId: Int) {
        skinDao.unequipAllSkins()
        skinDao.updateSkinEquipped(skinId, true)
    }

    override suspend fun unequipSkin(skinId: Int) {
        skinDao.updateSkinEquipped(skinId, false)
    }

    override suspend fun insertSkin(skin: Skin) {
        skinDao.insertSkin(skin.toEntity())
    }
}