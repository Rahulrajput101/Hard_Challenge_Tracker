package com.ondevop.core.domain.prefernces

import kotlinx.coroutines.flow.Flow


interface Preferences {

     suspend fun saveLoggedInfo(isLogin: Boolean)

     fun getLoggedInfo() : Flow<Boolean>

     companion object {
         const val Key_IS_LOG_IN = "is_log_in"
     }
}