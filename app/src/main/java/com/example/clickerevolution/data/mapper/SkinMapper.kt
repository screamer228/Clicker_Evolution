package com.example.clickerevolution.data.mapper

import com.example.clickerevolution.data.room.SkinEntity
import com.example.clickerevolution.presentation.model.CurrentSkin
import com.example.clickerevolution.presentation.model.Skin

fun SkinEntity.toSkin(): Skin {
    return Skin(
        id = id,
        title = title,
        imageId = imageId,
        soundId = soundId,
        price = price,
        isPurchased = isPurchased,
        isEquipped = isEquipped
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
        price = price,
        isPurchased = isPurchased,
        isEquipped = isEquipped
    )
}