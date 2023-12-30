package com.ondevop.settings_data.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.settings_data.repository.SettingsRepositoryImp
import com.ondevop.settings_domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsDataModule {

    @Singleton
    @Provides
    fun provideSettingRepository(
        @ApplicationContext app: Context,
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient,
        preferences: Preferences
    ): SettingsRepository {
        return SettingsRepositoryImp(
            context = app,
            firebaseAuth = firebaseAuth,
            googleSignInClient = googleSignInClient,
            prefernces = preferences
        )
    }


}