package com.ondevop.core.domain.prefernces

import kotlinx.coroutines.flow.Flow


interface Preferences {

     suspend fun saveLoggedInfo(isLogin: Boolean)
     suspend fun saveUserName(name: String)
     suspend fun saveProfileUri(uri: String)

     suspend fun saveGoal(goal : Int)

     fun getLoggedInfo() : Flow<Boolean>
     fun getUserName(): Flow<String>
     fun getProfileUri(): Flow<String>

     fun getGoal(): Flow<Int>

     suspend fun clearAllPreferenceData()

     companion object {
         const val Key_IS_LOG_IN = "is_log_in"
         const val Key_NAME= "name"
         const val Key_PROFILE_URI = "profile_uri"
         const val KEY_GOAL = "goal"
     }
}