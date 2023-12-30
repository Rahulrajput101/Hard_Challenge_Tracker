package com.ondevop.core_data.di

import android.app.Application
import androidx.room.Room
import com.ondevop.core_data.AndroidNotificationManagerImp
import com.ondevop.core_data.local.TrackerDatabase
import com.ondevop.core_data.repository.AndroidHabitAlarmScheduler
import com.ondevop.core_data.repository.SaveImageRepositoryImp
import com.ondevop.core_data.repository.TrackerRepositoryImp
import com.ondevop.core_domain.MyNotificationManager
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import com.ondevop.core_domain.repository.SaveImageRepository
import com.ondevop.core_domain.repository.TrackerRepository
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



    @Provides
    @Singleton
    fun provideMyNotificationManager(app: Application): MyNotificationManager {
        return AndroidNotificationManagerImp(app)
    }
    @Provides
    @Singleton
    fun provideHabitAlarmScheduler(
        app : Application
    ) : HabitAlarmScheduler {
        return AndroidHabitAlarmScheduler(app)
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
    ) : TrackerRepository {
        return TrackerRepositoryImp(trackerDatabase.trackerDao)
    }


}