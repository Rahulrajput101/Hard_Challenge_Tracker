package com.ondevop.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.ondevop.tracker_data.local.TrackerDao
import com.ondevop.tracker_data.local.TrackerDatabase
import com.ondevop.tracker_data.repository.TrackerRepositoryImp
import com.ondevop.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

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