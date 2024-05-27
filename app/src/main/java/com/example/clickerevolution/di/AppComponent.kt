package com.example.clickerevolution.di

import com.example.clickerevolution.presentation.HomeFragment
import com.example.clickerevolution.presentation.shop_fragment.ShopFragment
import com.example.clickerevolution.presentation.upgrade_fragment.UpgradesFragment
import com.example.clickerevolution.presentation.host.HostActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, DataModule::class])
interface AppComponent {

    fun injectHostActivity(hostActivity: HostActivity)
    fun injectHomeFragment(homeFragment: HomeFragment)
    fun injectShopFragment(shopFragment: ShopFragment)
    fun injectUpgradeFragment(upgradesFragment: UpgradesFragment)

}