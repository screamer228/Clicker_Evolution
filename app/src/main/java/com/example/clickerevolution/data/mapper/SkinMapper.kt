package com.example.clickerevolution.data.mapper

import com.example.clickerevolution.data.room.SkinEntity
import com.example.clickerevolution.presentation.model.Skin

fun SkinEntity.toSkin(): Skin {
    return Skin(
        id = id,
        title = title,
        imageId = imageId,
        price = price,
        isPurchased = isPurchased,
        isEquipped = isEquipped
    )
}

fun Skin.toEntity(): SkinEntity {
    return SkinEntity(
        id = id,
        title = title,
        imageId = imageId,
        price = price,
        isPurchased = isPurchased,
        isEquipped = isEquipped
    )
}