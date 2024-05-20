package com.example.clickerevolution.di

import android.content.Context
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import com.example.clickerevolution.data.repository.resources.ResourcesRepository
import com.example.clickerevolution.data.repository.skins.SkinsRepository
import com.example.clickerevolution.presentation.viewmodel.SharedViewModelFactory
import com.example.clickerevolution.presentation.viewmodel.ShopViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

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
    fun provideContext(): Context {
        return context
    }
}