package com.example.clickerevolution.data.repository.skins

import android.util.Log
import com.example.clickerevolution.R
import com.example.clickerevolution.data.room.skins.mapper.toCurrentSkin
import com.example.clickerevolution.data.room.skins.mapper.toEntity
import com.example.clickerevolution.data.room.skins.mapper.toSkin
import com.example.clickerevolution.data.room.skins.SkinDao
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkinsRepositoryImpl @Inject constructor(
    private val skinDao: SkinDao
) : SkinsRepository {

//    override val allSkins: Flow<List<Skin>> = skinDao.getAllSkins().map { entities ->
//        entities.map { it.toSkin() }
//    }

    override suspend fun getAllSkins(): List<Skin> {
        val skinList = skinDao.getAllSkins()
        return skinList.map { it.toSkin() }
    }

    override suspend fun prepopulateSkinsDatabase() {
        val initialSkinsList = listOf(
            SkinEntity(1, "Skin 1", R.drawable.img_skin1, R.raw.sound_click, 100),
            SkinEntity(2, "Skin 2", R.drawable.img_skin2, R.raw.sound_cookie_click, 300),
            SkinEntity(3, "Skin 3", R.drawable.img_skin3, R.raw.sound_cookie_click, 500),
            SkinEntity(4, "Skin 4", R.drawable.img_skin4, R.raw.sound_cookie_click, 1000),
            SkinEntity(5, "Skin 5", R.drawable.img_skin5, R.raw.sound_cookie_click, 2000),
        )
        skinDao.insertSkins(initialSkinsList)
        Log.d("populateDatabase check", "prepopulateSkinsDatabase()")
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