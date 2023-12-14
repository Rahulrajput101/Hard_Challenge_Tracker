package com.ondevop.core.data.prefrences.di

import android.app.Application
import com.ondevop.core.data.prefrences.DefaultPreferences
import com.ondevop.core.data.prefrences.dataStore
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.core.domain.repository.SaveImageRepository
import com.ondevop.core.domain.use_cases.SaveImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object CoreDomainModule {

    @Provides
    @ViewModelScoped
    fun provideSaveImage(
        repository: SaveImageRepository
    ): SaveImage{
        return SaveImage(repository)
    }
}