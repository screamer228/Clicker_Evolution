package com.example.clickerevolution.data.repository.prefs

import android.content.SharedPreferences
import clickerevolution.BuildConfig
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrefsRepository {

    override suspend fun saveGoldValueInPrefs(value: String) {
        with(sharedPreferences.edit()) {
            putString(BuildConfig.PREFS_TITLE_KEY, value)
            apply()
        }
    }

    override suspend fun getGoldValueFromPrefs(): String {
        return sharedPreferences.getString(
            BuildConfig.PREFS_TITLE_KEY,
            BuildConfig.PREFS_DEFAULT_VALUE
        )
            ?: BuildConfig.PREFS_DEFAULT_VALUE
    }
}