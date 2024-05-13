package com.example.clickerevolution.app

import android.app.Application
import com.example.clickerevolution.di.AppComponent
import com.example.clickerevolution.di.AppModule
import com.example.clickerevolution.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}