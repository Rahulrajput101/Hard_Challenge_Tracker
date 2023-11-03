package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithEmailAndPassword @Inject constructor(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email : String , password: String) : Result<String> {
          return repository.loginWithEmailPassword(email,password)
    }
}