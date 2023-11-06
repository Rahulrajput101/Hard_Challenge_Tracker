package com.ondevop.login_data.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ondevop.login_domain.model.UserInfo
import com.ondevop.login_domain.repository.GoogleAuthRepository
import javax.inject.Inject

class GoogleAuthRepositoryImp @Inject constructor(
    private val app: Application,
   private val googleSignInClient: GoogleSignInClient
    ): GoogleAuthRepository {
    override suspend fun sigIn(): Result<UserInfo> {
        googleSignInClient.signInIntent
      //  GoogleSignIn.getSignedInAccountFromIntent()
        return Result.success(UserInfo("ehaul",null))

    }

    override suspend fun getSignInIntent(): Intent {
        TODO("Not yet implemented")
    }
}