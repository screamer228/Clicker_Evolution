package com.example.clickerevolution.data.room.resources.mapper

import com.example.clickerevolution.data.room.resources.entity.ResourcesEntity
import com.example.clickerevolution.presentation.model.Resources

fun ResourcesEntity.toResources(): Resources {
    return Resources(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue
    )
}

fun Resources.toEntity(): ResourcesEntity {
    return ResourcesEntity(
        goldClickTickValue = goldClickTickValue,
        goldTickPerSecValue = goldTickPerSecValue
    )
}