package com.ondevop.login_domain.repository

import com.ondevop.login_domain.model.UserInfo

interface AuthRepository {
    suspend fun loginWithEmailPassword(email: String, password: String): Result<UserInfo>

    suspend fun loginWithGoogle(id : String): Result<UserInfo>
    suspend fun registerUserWithEmailPassword(email: String,password: String,name :String ,profileUri: String?) : Result<String>

    suspend fun isUserSignedIn(): Boolean
}