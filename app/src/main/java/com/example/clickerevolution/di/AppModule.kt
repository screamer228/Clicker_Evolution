package com.example.clickerevolution.di

import android.content.Context
import android.util.Log
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository
import com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel.DailyRewardsViewModelFactory
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.shop_fragment.viewmodel.ShopViewModelFactory
import com.example.clickerevolution.presentation.upgrades_fragment.viewmodel.UpgradesViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDailyRewardsViewModelFactory(
        prefsRepository: PrefsRepository
    ): DailyRewardsViewModelFactory {
        return DailyRewardsViewModelFactory(
            prefsRepository
        )
    }

    @Provides
    @Singleton
    fun provideUpgradesViewModelFactory(
        upgradesRepository: UpgradesRepository
    ): UpgradesViewModelFactory {
        return UpgradesViewModelFactory(
            upgradesRepository
        )
    }

    @Provides
    @Singleton
    fun provideShopViewModelFactory(
        skinsRepository: SkinsRepository
    ): ShopViewModelFactory {
        return ShopViewModelFactory(
            skinsRepository
        )
    }

    @Provides
    @Singleton
    fun provideSharedViewModelFactory(
        prefsRepository: PrefsRepository,
        skinsRepository: SkinsRepository,
        statsRepository: StatsRepository
    ): SharedViewModelFactory {
        return SharedViewModelFactory(
            prefsRepository,
            skinsRepository,
            statsRepository
        )
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}