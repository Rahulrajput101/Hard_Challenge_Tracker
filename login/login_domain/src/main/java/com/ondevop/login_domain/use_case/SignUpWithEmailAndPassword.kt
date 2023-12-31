package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpWithEmailAndPassword @Inject constructor(
   private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String,name:String ,profileUri: String? = null): Result<String>{
        return withContext(Dispatchers.IO){
            repository.registerUserWithEmailPassword(email, password,name,profileUri)
        }
    }
}