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

@Module
class RepositoryModule {

    @Provides
    fun provideResourcesRepository(resourcesDao: ResourcesDao): ResourcesRepository {
        return ResourcesRepositoryImpl(resourcesDao)
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
}