package com.ondevop.login_domain.use_case

import com.ondevop.login_domain.repository.AuthRepository
import javax.inject.Inject

class IsSignedIn @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean{
        return repository.isUserSignedIn()
    }
}