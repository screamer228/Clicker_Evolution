package com.example.clickerevolution.data.repository.prefs

import android.content.SharedPreferences
import android.util.Log
import com.example.clickerevolution.BuildConfig
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrefsRepository {

    init {
        Log.d("sharedPrefs check", "PrefsRepositoryImpl initialized with $sharedPreferences")
    }

    override suspend fun saveGoldValueInPrefs(value: String) {
        Log.d("sharedPrefs check", "inRepository when save: $value")
        with(sharedPreferences.edit()) {
            putString(BuildConfig.PREFS_CURRENT_GOLD_TITLE_KEY, value)
            apply()
        }
    }

    override suspend fun getGoldValueFromPrefs(): String {
        val value = sharedPreferences.getString(
            BuildConfig.PREFS_CURRENT_GOLD_TITLE_KEY,
            BuildConfig.PREFS_CURRENT_GOLD_DEFAULT_VALUE
        )
            ?: BuildConfig.PREFS_CURRENT_GOLD_DEFAULT_VALUE
        Log.d("sharedPrefs check", "prefs default value: ${BuildConfig.PREFS_CURRENT_GOLD_DEFAULT_VALUE}")
        Log.d("sharedPrefs check", "inRepository when get: $value")
        return value
    }
}