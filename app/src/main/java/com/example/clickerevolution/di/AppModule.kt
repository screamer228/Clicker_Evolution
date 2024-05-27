package com.example.clickerevolution.di

import android.content.Context
import android.util.Log
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.shop_fragment.viewmodel.ShopViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

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
        resourcesRepository: ResourcesRepository
    ): SharedViewModelFactory {
        return SharedViewModelFactory(
            prefsRepository,
            skinsRepository,
            resourcesRepository
        )
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        Log.d("sharedPrefs check", "providing application context $context")
        return context
    }
}