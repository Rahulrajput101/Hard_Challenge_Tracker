package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository

class SignInWithEmailAndPassword(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email : String , password: String) : Result<String> {
          return repository.loginWithEmailPassword(email,password)
    }
}