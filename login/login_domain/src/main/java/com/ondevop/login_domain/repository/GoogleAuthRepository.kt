package com.ondevop.login_domain.repository

import android.content.Intent
import com.ondevop.login_domain.model.UserInfo

interface GoogleAuthRepository {

    suspend fun sigIn(): Result<UserInfo>

    suspend fun getSignInIntent(): Intent
}