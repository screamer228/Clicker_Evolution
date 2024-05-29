package com.example.clickerevolution.data.repository.prefs

interface PrefsRepository {
    suspend fun saveGoldValueInPrefs(value: String)
    suspend fun getGoldValueFromPrefs(): String
    suspend fun saveLastExitTime(time: Long)
    suspend fun getLastExitTime(): Long
}