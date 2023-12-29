package com.ondevop.settings_data.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.ondevop.settings_domain.repository.SettingsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SettingsRepositoryImp (
    private val context : Context,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : SettingsRepository{
    override suspend fun signOut(): Result<Unit> {
        return try {
             firebaseAuth.signOut()
            // Check if the user is signed in with Google
            val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context)
            if (googleSignInAccount != null) {
                // If signed in with Google, sign out
                googleSignInClient.signOut().await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}