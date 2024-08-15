package com.example.clickerevolution.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clickerevolution.data.repository.notification.NotificationRepository
import com.example.clickerevolution.data.repository.notification.NotificationRepositoryImpl
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.prefs.PrefsRepositoryImpl
import com.example.clickerevolution.data.repository.stats.StatsRepository
import com.example.clickerevolution.data.repository.stats.StatsRepositoryImpl
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepositoryImpl
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepository
import com.example.clickerevolution.data.repository.upgrades.UpgradesRepositoryImpl
import com.example.clickerevolution.data.room.stats.StatsDao
import com.example.clickerevolution.data.room.skins.SkinDao
import com.example.clickerevolution.data.room.upgrades.UpgradeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNotificationScheduler(context: Context) : NotificationRepository {
        return NotificationRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideUpgradesRepository(upgradeDao: UpgradeDao): UpgradesRepository {
        return UpgradesRepositoryImpl(upgradeDao)
    }

    @Provides
    @Singleton
    fun provideStatsRepository(statsDao: StatsDao): StatsRepository {
        return StatsRepositoryImpl(statsDao)
    }

    @Provides
    @Singleton
    fun provideSkinRepository(skinDao: SkinDao): SkinsRepository {
        return SkinsRepositoryImpl(skinDao)
    }

    @Provides
    @Singleton
    fun providesSharedPrefsRepository(
        sharedPreferences: SharedPreferences
    ): PrefsRepository {
        return PrefsRepositoryImpl(sharedPreferences)
    }
}