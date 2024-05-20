package com.example.clickerevolution.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clickerevolution.BuildConfig
import com.example.clickerevolution.data.room.resources.ResourcesDao
import com.example.clickerevolution.data.room.resources.ResourcesDatabase
import com.example.clickerevolution.data.room.skins.SkinDao
import com.example.clickerevolution.data.room.skins.SkinsDatabase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideResourcesDao(database: ResourcesDatabase): ResourcesDao {
        return database.resourcesDao()
    }

    @Provides
    fun provideResourcesDatabase(context: Context): ResourcesDatabase {
        return ResourcesDatabase.getDatabase(context)
    }

    @Provides
    fun provideSkinDao(database: SkinsDatabase): SkinDao {
        return database.skinDao()
    }

    @Provides
    fun provideSkinsDatabase(context: Context): SkinsDatabase {
        return SkinsDatabase.getDatabase(context)
    }

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.PREFS_NAME, Context.MODE_PRIVATE)
    }

//    @Provides
//    fun provideContext(): Context {
//        return context
//    }
}