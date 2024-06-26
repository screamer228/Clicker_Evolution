package com.example.clickerevolution.app

import android.app.Application
import com.example.clickerevolution.di.AppComponent
import com.example.clickerevolution.di.AppModule
import com.example.clickerevolution.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

//        fun clearPreferences(name: String) {
//            val sharedPreferences: SharedPreferences = getSharedPreferences(name, Context.MODE_PRIVATE)
//            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//            editor.clear()
//            editor.apply()
//        }
//
//        clearPreferences("login_streak")
//        clearPreferences("daily_reward_available")

//        clearSharedPreferences()
        deleteDatabase("stats_db")
        deleteDatabase("skins_db")
        deleteDatabase("upgrades_db")

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}