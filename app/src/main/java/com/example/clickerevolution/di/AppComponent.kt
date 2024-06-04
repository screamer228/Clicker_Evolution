package com.example.clickerevolution.di

import com.example.clickerevolution.presentation.dialog_fragment.DialogFragment
import com.example.clickerevolution.presentation.HomeFragment
import com.example.clickerevolution.presentation.shop_fragment.ShopFragment
import com.example.clickerevolution.presentation.upgrades_fragment.UpgradesClickFragment
import com.example.clickerevolution.presentation.host.HostActivity
import com.example.clickerevolution.presentation.upgrades_fragment.UpgradesFragment
import com.example.clickerevolution.presentation.upgrades_fragment.UpgradesPerSecFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, DataModule::class])
interface AppComponent {

    fun injectHostActivity(hostActivity: HostActivity)
    fun injectHomeFragment(homeFragment: HomeFragment)
    fun injectShopFragment(shopFragment: ShopFragment)
    fun injectUpgradesFragment(upgradesFragment: UpgradesFragment)
    fun injectUpgradesClickFragment(upgradesClickFragment: UpgradesClickFragment)
    fun injectUpgradesPerSecFragment(upgradesPerSecFragment: UpgradesPerSecFragment)
    fun injectDialogFragment(dialogFragment: DialogFragment)
}