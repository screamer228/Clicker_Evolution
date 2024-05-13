package com.example.clickerevolution.di

import com.example.clickerevolution.presentation.HomeFragment
import com.example.clickerevolution.presentation.ShopFragment
import com.example.clickerevolution.presentation.UpgradeFragment
import com.example.clickerevolution.presentation.host.HostActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectHostActivity(hostActivity: HostActivity)
    fun injectHomeFragment(homeFragment: HomeFragment)
    fun injectShopFragment(shopFragment: ShopFragment)
    fun injectUpgradeFragment(upgradeFragment: UpgradeFragment)

}