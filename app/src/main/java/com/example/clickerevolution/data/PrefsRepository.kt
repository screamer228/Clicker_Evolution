package com.example.clickerevolution.data

interface PrefsRepository {
    suspend fun saveGoldValueInPrefs(value: String)
    suspend fun getGoldValueFromPrefs() : String
}