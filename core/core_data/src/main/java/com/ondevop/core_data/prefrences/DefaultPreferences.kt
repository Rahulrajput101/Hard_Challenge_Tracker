package com.ondevop.core_data.prefrences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    "default_pref"
)

private val isLoggedInKey = booleanPreferencesKey(Preferences.Key_IS_LOG_IN)
private val nameKey = stringPreferencesKey(Preferences.Key_NAME)
private val profileUriKey =stringPreferencesKey(Preferences.Key_PROFILE_URI)
private val goalKey = intPreferencesKey(Preferences.KEY_GOAL)
private val proVersionKey = booleanPreferencesKey(Preferences.KEY_PRO_VERSION)
private val isOnboardingCompletedKey = booleanPreferencesKey(Preferences.KEY_IS_ONBOARDING_COMPLETED)
private val isAlarmScheduledKey = booleanPreferencesKey(Preferences.KEY_IS_ALARM_SCHEDULED)

class DefaultPreferences(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) :
    Preferences {
    override suspend fun saveLoggedInfo(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[isLoggedInKey] = isLogin
        }
    }

    override suspend fun saveUserName(name: String) {
       dataStore.edit { preferences ->
           preferences[nameKey] = name
       }
    }

    override suspend fun saveProfileUri(uri: String) {
        dataStore.edit {preferences ->
            preferences[profileUriKey] = uri
        }
    }

    override suspend fun saveGoal(goal: Int) {
        dataStore.edit { preferences ->
            preferences[goalKey] = goal
        }
    }

    override suspend fun saveIsOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preference ->
            preference[isOnboardingCompletedKey] = completed
        }
    }

    override suspend fun saveAlarmSchedule(isSchedule: Boolean) {
        dataStore.edit { perference->
            perference[isAlarmScheduledKey] = isSchedule

        }
    }

    override suspend fun saveIsProVersion(isProVersion: Boolean) {
        dataStore.edit { preference ->
            preference[proVersionKey] = isProVersion
        }
    }

    override fun getIsProVersion(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[proVersionKey] ?: Constant.DEFAULT_IS_PRO_VERSION
    }

    override fun getLoggedInfo(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[isLoggedInKey] ?: Constant.DEFAULT_IS_LOGGED_IN
    }

    override fun getUserName(): Flow<String>  = dataStore.data.map { preference ->
        preference[nameKey] ?: Constant.DEFAULT_NAME
    }

    override fun getProfileUri(): Flow<String>  = dataStore.data.map { preference ->
        preference[profileUriKey] ?: Constant.DEFAULT_PROFILE_URI
    }

    override fun getGoal(): Flow<Int> = dataStore.data.map { preference->
        preference[goalKey] ?: Constant.Default_DAYS_GOAL
    }

    override fun getIsOnboardingCompleted(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[isOnboardingCompletedKey] ?: Constant.DEFAULT_IS_ONBOARDING_COMPLETED
    }

    override fun getAlarmScheduled(): Flow<Boolean> = dataStore.data.map { preference ->
        preference[isAlarmScheduledKey] ?: Constant.DEFAULT_IS_ALARM_SCHEDULED
    }

    override suspend fun clearAllPreferenceData() {
        dataStore.edit {preferences ->
            preferences[nameKey] = Constant.DEFAULT_NAME
            preferences[profileUriKey] = Constant.DEFAULT_PROFILE_URI
            preferences[isLoggedInKey] = Constant.DEFAULT_IS_LOGGED_IN
        }
    }
}
