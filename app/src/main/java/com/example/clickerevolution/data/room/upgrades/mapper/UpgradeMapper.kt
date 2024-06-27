package com.example.clickerevolution.data.room.upgrades.mapper

import com.example.clickerevolution.common.Price
import com.example.clickerevolution.data.room.upgrades.entity.UpgradeEntity
import com.example.clickerevolution.presentation.model.Upgrade

fun UpgradeEntity.toUpgrade() : Upgrade {
    return Upgrade(
        id = id,
        title = title,
        power = power,
        level = level,
        price = Price(priceType, priceValue),
        type = type
    )
}