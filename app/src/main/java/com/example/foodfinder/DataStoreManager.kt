package com.example.foodfinder

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("product_history")

class DataStoreManager(private val context: Context) {
    private val PRODUCT_HISTORY_KEY = stringPreferencesKey("product_history")

    val productHistory: Flow<List<String>> =
        context.dataStore.data
            .map { preferences ->
                preferences[PRODUCT_HISTORY_KEY]?.split(",") ?: emptyList()
            }

    suspend fun saveProductToHistory(productId: String) {
        context.dataStore.edit { preferences ->
            val currentHistory = preferences[PRODUCT_HISTORY_KEY]?.split(",") ?: emptyList()
            val updatedHistory = (listOf(productId) + currentHistory).distinct().take(10)
            preferences[PRODUCT_HISTORY_KEY] = updatedHistory.joinToString(",")
        }
    }
}
