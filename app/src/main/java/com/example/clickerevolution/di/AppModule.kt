package com.example.clickerevolution.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clickerevolution.BuildConfig
import com.example.clickerevolution.data.repository.PrefsRepository
import com.example.clickerevolution.data.repository.PrefsRepositoryImpl
import com.example.clickerevolution.data.repository.SkinsRepository
import com.example.clickerevolution.data.repository.SkinsRepositoryImpl
import com.example.clickerevolution.data.room.SkinDao
import com.example.clickerevolution.data.room.SkinsDatabase
import com.example.clickerevolution.presentation.viewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.viewmodel.ShopViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideShopViewModelFactory(
        skinsRepository: SkinsRepository
    ): ShopViewModelFactory {
        return ShopViewModelFactory(
            skinsRepository
        )
    }

    @Provides
    fun provideSharedViewModelFactory(
        prefsRepository: PrefsRepository,
        skinsRepository: SkinsRepository
    ): SharedViewModelFactory {
        return SharedViewModelFactory(
            prefsRepository,
            skinsRepository
        )
    }

    @Provides
    fun provideSkinRepository(skinDao: SkinDao): SkinsRepository {
        return SkinsRepositoryImpl(skinDao)
    }

    @Provides
    fun providesSharedPrefsRepository(
        sharedPreferences: SharedPreferences
    ): PrefsRepository {
        return PrefsRepositoryImpl(sharedPreferences)
    }

    @Provides
    fun provideDatabase(context: Context): SkinsDatabase {
        return SkinsDatabase.getDatabase(context)
    }

    @Provides
    fun provideSkinDao(database: SkinsDatabase): SkinDao {
        return database.skinDao()
    }

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideContext(): Context {
        return context
    }
}