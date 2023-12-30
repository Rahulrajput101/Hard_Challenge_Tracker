package com.ondevop.core_domain.di

import android.app.Application
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import com.ondevop.core_domain.repository.SaveImageRepository
import com.ondevop.core_domain.repository.TrackerRepository
import com.ondevop.core_domain.use_cases.SaveImage
import com.ondevop.core_domain.use_cases.SchedulingHabitAlarm
import com.ondevop.core_domain.use_cases.ToShowNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDomainModule {

    @Provides
    @Singleton
    fun provideSaveImage(
        repository: SaveImageRepository
    ): SaveImage {
        return SaveImage(repository)
    }


    @Provides
    @Singleton
    fun provideToShowNotification(
        repository: TrackerRepository
    ): ToShowNotification {
        return  ToShowNotification(repository)
    }

    @Provides
    @Singleton
    fun provideSchedulingHabitAlarm(
        scheduler: HabitAlarmScheduler
    ) : SchedulingHabitAlarm {
        return SchedulingHabitAlarm(scheduler)
    }

}