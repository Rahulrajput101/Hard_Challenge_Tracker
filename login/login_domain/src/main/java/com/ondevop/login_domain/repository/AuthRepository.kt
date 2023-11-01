package com.ondevop.login_domain.repository
interface AuthRepository {
    suspend fun loginWithEmailPassword(email: String, password: String): Result<String>
    suspend fun registerUserWithEmailPassword(email: String,password: String) : Result<String>
}