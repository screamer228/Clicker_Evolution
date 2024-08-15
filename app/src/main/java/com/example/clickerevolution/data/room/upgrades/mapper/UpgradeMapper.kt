package com.example.clickerevolution.data.room.upgrades.mapper

import com.example.clickerevolution.common.Price
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.room.upgrades.entity.UpgradeEntity
import com.example.clickerevolution.presentation.model.Upgrade

fun UpgradeEntity.toUpgrade() : Upgrade {
    return Upgrade(
        id = id,
        title = title,
        description = description,
        imageId = imageId,
        power = power,
        level = level,
        price = Price(
            priceType,
            if (type == UpgradeType.SPECIAL) priceMap[level]!! else priceValue
        ),
        type = type
    )
}

val priceMap = mapOf(
    1 to 1,
    2 to 15,
    3 to 30,
    4 to 50,
    5 to 75,
    6 to 100,
    7 to 150,
    8 to 250,
    9 to 500
)
