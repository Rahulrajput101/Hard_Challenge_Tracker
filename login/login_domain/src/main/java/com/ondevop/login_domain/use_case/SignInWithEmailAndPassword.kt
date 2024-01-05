package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.model.UserInfo
import com.ondevop.login_domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithEmailAndPassword @Inject constructor(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email : String , password: String) : Result<UserInfo> {
       return withContext(Dispatchers.IO){
            repository.loginWithEmailPassword(email,password)
        }

    }
}