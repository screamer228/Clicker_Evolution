package com.example.clickerevolution.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clickerevolution.BuildConfig
import com.example.clickerevolution.data.PrefsRepository
import com.example.clickerevolution.data.PrefsRepositoryImpl
import com.example.clickerevolution.presentation.SharedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideSharedViewModelFactory(
        prefsRepository: PrefsRepository
    ): SharedViewModelFactory {
        return SharedViewModelFactory(
            prefsRepository
        )
    }

    @Provides
    fun providesSharedPrefsRepository(
        sharedPreferences: SharedPreferences
    ): PrefsRepository {
        return PrefsRepositoryImpl(sharedPreferences)
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