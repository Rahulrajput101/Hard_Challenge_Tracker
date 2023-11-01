package com.ondevop.login_data.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.ondevop.login_data.repository.AuthRepositoryImp
import com.ondevop.login_domain.repository.AuthRepository
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

}