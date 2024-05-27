package com.example.clickerevolution.di

import android.content.SharedPreferences
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.prefs.PrefsRepositoryImpl
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepositoryImpl
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepositoryImpl
import com.example.clickerevolution.data.room.resources.ResourcesDao
import com.example.clickerevolution.data.room.skins.SkinDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideResourcesRepository(resourcesDao: ResourcesDao): ResourcesRepository {
        return ResourcesRepositoryImpl(resourcesDao)
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