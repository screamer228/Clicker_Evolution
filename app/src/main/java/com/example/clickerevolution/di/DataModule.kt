package com.example.clickerevolution.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clickerevolution.BuildConfig
import com.example.clickerevolution.data.room.stats.StatsDao
import com.example.clickerevolution.data.room.stats.StatsDatabase
import com.example.clickerevolution.data.room.skins.SkinDao
import com.example.clickerevolution.data.room.skins.SkinsDatabase
import com.example.clickerevolution.data.room.upgrades.UpgradeDao
import com.example.clickerevolution.data.room.upgrades.UpgradesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideUpgradeDao(database: UpgradesDatabase): UpgradeDao {
        return database.upgradeDao()
    }

    @Provides
    @Singleton
    fun provideUpgradesDatabase(context: Context): UpgradesDatabase {
        return UpgradesDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideStatsDao(database: StatsDatabase): StatsDao {
        return database.statsDao()
    }

    @Provides
    @Singleton
    fun provideStatsDatabase(context: Context): StatsDatabase {
        return StatsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideSkinDao(database: SkinsDatabase): SkinDao {
        return database.skinDao()
    }

    @Provides
    @Singleton
    fun provideSkinsDatabase(context: Context): SkinsDatabase {
        return SkinsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.SHARED_PREFS, Context.MODE_PRIVATE)
    }
}