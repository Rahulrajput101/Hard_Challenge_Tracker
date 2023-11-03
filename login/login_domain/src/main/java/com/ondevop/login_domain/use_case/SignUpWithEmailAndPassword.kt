package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository

class SignUpWithEmailAndPassword (
   private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Result<String>{
        return repository.registerUserWithEmailPassword(email, password)
    }
}