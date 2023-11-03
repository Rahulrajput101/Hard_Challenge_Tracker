package com.ondevop.core.data.prefrences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.core.uitl.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    "default_pref"
)

private val isLoggedInKey = booleanPreferencesKey(Preferences.Key_IS_LOG_IN)

class DefaultPreferences(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) :
    Preferences {
    override suspend fun saveLoggedInfo(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[isLoggedInKey] = isLogin
        }
    }

    override fun getLoggedInfo(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[isLoggedInKey] ?: Constant.DEFAULT_IS_LOGGED_IN
    }
}