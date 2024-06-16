package com.example.clickerevolution.data.repository.prefs

import android.content.SharedPreferences
import com.example.clickerevolution.BuildConfig
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrefsRepository {

    override suspend fun saveGoldValueInPrefs(value: String) {
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
        return value
    }

    override suspend fun saveDiamondValueInPrefs(value: String) {
        with(sharedPreferences.edit()) {
            putString(BuildConfig.PREFS_CURRENT_DIAMONDS_TITLE_KEY, value)
            apply()
        }
    }

    override suspend fun getDiamondValueFromPrefs(): String {
        val value = sharedPreferences.getString(
            BuildConfig.PREFS_CURRENT_DIAMONDS_TITLE_KEY,
            BuildConfig.PREFS_CURRENT_DIAMONDS_DEFAULT_VALUE
        )
            ?: BuildConfig.PREFS_CURRENT_DIAMONDS_DEFAULT_VALUE
        return value
    }

    override fun saveLastExitTime(time: Long) {
        with(sharedPreferences.edit()) {
            putLong(BuildConfig.PREFS_LAST_EXIT_TIME_TITLE_KEY, time)
            apply()
        }
    }

    override fun getLastExitTime(): Long {
        val value = sharedPreferences.getLong(
            BuildConfig.PREFS_LAST_EXIT_TIME_TITLE_KEY,
            BuildConfig.PREFS_LAST_EXIT_TIME_DEFAULT_VALUE
        )
            ?: BuildConfig.PREFS_LAST_EXIT_TIME_DEFAULT_VALUE
        return value
    }

    override fun getLoginStreak(): Int {
        return sharedPreferences.getInt("login_streak", 0)
    }

    override fun saveLoginStreak(streak: Int) {
        sharedPreferences.edit().putInt("login_streak", streak).apply()
    }

    override fun isDailyRewardAvailable(): Boolean {
        return sharedPreferences.getBoolean("daily_reward_available", false)
    }

    override fun setDailyRewardAvailable(available: Boolean) {
        sharedPreferences.edit().putBoolean("daily_reward_available", available).apply()
    }
}