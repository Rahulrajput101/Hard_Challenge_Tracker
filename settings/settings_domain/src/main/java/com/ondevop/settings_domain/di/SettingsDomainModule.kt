package com.ondevop.settings_domain.di

import com.ondevop.settings_domain.repository.SettingsRepository
import com.ondevop.settings_domain.use_case.SignOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object SettingsDomainModule {

    @Provides
    @ViewModelScoped
    fun provideSignOut(
        repository: SettingsRepository
    ): SignOut{
        return SignOut(repository)
    }

}
