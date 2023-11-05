package com.ondevop.login_data.repository

import android.app.Application
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.ondevop.login_domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await


class AuthRepositoryImp(
    context: Application,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {


    override suspend fun loginWithEmailPassword(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.success("Login successful")
            } else {
                Result.failure(Exception("Login Failed "))
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("User does not exist"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid email or password"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun registerUserWithEmailPassword(
        email: String,
        password: String,
        name: String,
        profileUri: String?,
    ): Result<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {

                val user = result.user!!
                val profileInfoBuilder = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                if (profileUri != null) {
                    profileInfoBuilder.photoUri = profileUri.toUri()
                }
                val profileInfo = profileInfoBuilder.build()

                user.updateProfile(profileInfo).await()
                Result.success(user.uid)
            } else {
                Result.failure(Exception("user is null"))
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            return Result.failure(Exception("Email is already in use"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Result.failure(Exception("Invalid email or password"))
        } catch (e: FirebaseAuthEmailException) {
            return Result.failure(Exception("Invalid email format"))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}