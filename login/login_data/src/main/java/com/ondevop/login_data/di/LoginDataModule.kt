package com.ondevop.login_data.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ondevop.login_data.repository.AuthRepositoryImp
import com.ondevop.login_data.repository.GoogleAuthRepositoryImp
import com.ondevop.login_domain.repository.AuthRepository
import com.ondevop.login_domain.repository.GoogleAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginDataModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(app: Application, firebaseAuth: FirebaseAuth) : AuthRepository {
        return  AuthRepositoryImp(app,firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideGoogleSignClient(app: Application) : GoogleSignInClient{
        return GoogleSignIn.getClient(
            app,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )
    }

    @Singleton
    @Provides
    fun provideGoogleAuthRepository(
        app: Application,
        googleSignInClient: GoogleSignInClient
    ) : GoogleAuthRepository {
        return GoogleAuthRepositoryImp(app,googleSignInClient)
    }


}