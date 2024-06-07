package com.example.clickerevolution.data.repository.prefs

interface PrefsRepository {
    suspend fun saveGoldValueInPrefs(value: String)
    suspend fun getGoldValueFromPrefs(): String
    suspend fun saveDiamondValueInPrefs(value: String)
    suspend fun getDiamondValueFromPrefs(): String
    suspend fun saveLastExitTime(time: Long)
    fun getLastExitTime(): Long
}