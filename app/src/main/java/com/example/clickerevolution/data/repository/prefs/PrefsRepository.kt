package com.example.clickerevolution.data.repository.prefs

interface PrefsRepository {
    suspend fun saveGoldValueInPrefs(value: String)
    suspend fun getGoldValueFromPrefs() : String
}