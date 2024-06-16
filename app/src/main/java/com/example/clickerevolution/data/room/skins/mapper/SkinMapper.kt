package com.example.clickerevolution.data.room.skins.mapper

import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin

fun SkinEntity.toSkin(): Skin {
    return Skin(
        id = id,
        title = title,
        imageId = imageId,
        soundId = soundId,
        price = Currency(priceType, priceValue),
        isPurchased = isPurchased,
        isEquipped = isEquipped,
        rarity = rarity
    )
}

fun SkinEntity.toCurrentSkin(): CurrentSkin {
    return CurrentSkin(
        imageId = imageId,
        soundId = soundId
    )
}

fun Skin.toEntity(): SkinEntity {
    return SkinEntity(
        id = id,
        title = title,
        imageId = imageId,
        soundId = soundId,
        priceType = price.type,
        priceValue = price.value,
        isPurchased = isPurchased,
        isEquipped = isEquipped,
        rarity = rarity
    )
}