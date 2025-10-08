package com.alenniboris.personalmanager.presentation.uikit.utils

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore("location_prefs")

class DatastoreRepository(
    private val apl: Application
) {

    suspend fun saveData(
        key: String,
        value: String
    ) {
        val datastoreKey = stringPreferencesKey(key)
        apl.dataStore.edit { prefs ->
            prefs[datastoreKey] = value
        }
    }

    suspend fun getData(key: String): String? {
        val datastoreKey = stringPreferencesKey(key)
        val prefs = apl.dataStore.data.first()
        val res = prefs[datastoreKey]
        return res ?: ""
    }
}