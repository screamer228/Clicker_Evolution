package com.example.clickerevolution.presentation.model

import com.example.clickerevolution.R

data class Resources(
    val gold: Int = 0,
    val skinsList: List<Skin> = listOf(
        Skin(
            "Черный",
            R.drawable.ic_launcher_background,
            500
        ),
        Skin(
            "Черный",
            R.drawable.ic_launcher_background,
            500
        ),
        Skin(
            "Черный",
            R.drawable.ic_launcher_background,
            500
        ),
        Skin(
            "Черный",
            R.drawable.ic_launcher_background,
            500
        )
    )
//    val upgradesList: List<Upgrade> = listOf()
)
