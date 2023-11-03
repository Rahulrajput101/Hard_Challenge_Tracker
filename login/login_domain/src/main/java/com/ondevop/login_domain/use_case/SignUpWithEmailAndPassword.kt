package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository
import javax.inject.Inject

class SignUpWithEmailAndPassword @Inject constructor(
   private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Result<String>{
        return repository.registerUserWithEmailPassword(email, password)
    }
}