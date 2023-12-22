package com.ondevop.core.di

import android.app.Application
import com.ondevop.core.data.repository.SaveImageRepositoryImp
import com.ondevop.core.domain.repository.SaveImageRepository
import com.ondevop.core.domain.use_cases.SaveImage
import dagger.Binds
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
    @Singleton
    fun provideSaveImage(
        repository: SaveImageRepository
    ): SaveImage {
        return SaveImage(repository)
    }


    @Provides
    @Singleton
    fun provideTrackerDatabase(app : Application) : TrackerDatabase {
        return Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            TrackerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackedChallengeRepository(
        trackerDatabase: TrackerDatabase
    ) : TrackerRepository{
        return TrackerRepositoryImp(trackerDatabase.trackerDao)
    }

}