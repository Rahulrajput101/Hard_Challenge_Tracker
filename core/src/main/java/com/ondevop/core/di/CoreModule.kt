package com.ondevop.core.di

import android.app.Application
import com.ondevop.core.data.repository.SaveImageRepositoryImp
import com.ondevop.core.domain.repository.SaveImageRepository
import com.ondevop.core.domain.use_cases.SaveImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideSaveImageRepository(
        app : Application
    ): SaveImageRepository {
        return SaveImageRepositoryImp(app)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveImage(
        repository: SaveImageRepository
    ): SaveImage {
        return SaveImage(repository)
    }

}