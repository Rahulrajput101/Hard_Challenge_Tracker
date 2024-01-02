package com.ondevop.core_domain.prefernces

import kotlinx.coroutines.flow.Flow


interface Preferences {

     suspend fun saveLoggedInfo(isLogin: Boolean)
     suspend fun saveUserName(name: String)
     suspend fun saveProfileUri(uri: String)
     suspend fun saveGoal(goal : Int)
    suspend fun saveIsOnboardingCompleted(completed: Boolean)

    suspend fun saveAlarmSchedule(isSchedule : Boolean)

     fun getLoggedInfo() : Flow<Boolean>
     fun getUserName(): Flow<String>
     fun getProfileUri(): Flow<String>
     fun getGoal(): Flow<Int>
    fun getIsOnboardingCompleted(): Flow<Boolean>

    fun getAlarmScheduled(): Flow<Boolean>

     suspend fun clearAllPreferenceData()

     companion object {
         const val Key_IS_LOG_IN = "is_log_in"
         const val Key_NAME= "name"
         const val Key_PROFILE_URI = "profile_uri"
         const val KEY_GOAL = "goal"
         const val KEY_IS_ONBOARDING_COMPLETED = "is_onboarding_completed"
         const val KEY_IS_ALARM_SCHEDULED = "is_alarm_scheduled"
     }
}