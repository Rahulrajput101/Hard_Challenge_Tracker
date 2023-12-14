package com.ondevop.core.data.di

import android.app.Application
import com.ondevop.core.data.repository.SaveImageRepositoryImp
import com.ondevop.core.domain.repository.SaveImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {
    @Provides
    @Singleton
    fun provideSaveImageRepository(
        app : Application
    ): SaveImageRepository {
        return SaveImageRepositoryImp(app)
    }
}