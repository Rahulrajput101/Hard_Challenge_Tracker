package com.ondevop.core.di

import android.app.Application
import androidx.room.Room
import com.ondevop.core.data.AndroidNotificationManagerImp
import com.ondevop.core.data.local.TrackerDatabase
import com.ondevop.core.data.repository.AndroidHabitAlarmScheduler
import com.ondevop.core.data.repository.SaveImageRepositoryImp
import com.ondevop.core.data.repository.TrackerRepositoryImp
import com.ondevop.core.domain.MyNotificationManager
import com.ondevop.core.domain.repository.HabitAlarmScheduler
import com.ondevop.core.domain.repository.SaveImageRepository
import com.ondevop.core.domain.repository.TrackerRepository
import com.ondevop.core.domain.use_cases.SaveImage
import com.ondevop.core.domain.use_cases.SchedulingHabitAlarm
import com.ondevop.core.domain.use_cases.ToShowNotification
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
    fun provideToShowNotification(
        repository: TrackerRepository
    ): ToShowNotification{
        return  ToShowNotification(repository)
    }

    @Provides
    @Singleton
    fun provideSchedulingHabitAlarm(
        scheduler: HabitAlarmScheduler
    ) : SchedulingHabitAlarm {
        return SchedulingHabitAlarm(scheduler)
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