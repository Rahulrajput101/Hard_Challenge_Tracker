package com.ondevop.core_domain.di

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import com.ondevop.core_domain.repository.SaveImageRepository
import com.ondevop.core_domain.repository.TrackerRepository
import com.ondevop.core_domain.use_cases.CreateTempPathForImage
import com.ondevop.core_domain.use_cases.SaveImage
import com.ondevop.core_domain.use_cases.SaveImageBitmap
import com.ondevop.core_domain.use_cases.SchedulingHabitAlarm
import com.ondevop.core_domain.use_cases.ToShowNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    fun provideSaveImageBitmap(
        repository: SaveImageRepository
    ): SaveImageBitmap {
        return SaveImageBitmap(repository)
    }
    @Provides
    @Singleton
    fun provideCreateTempPathForImage(
        repository: SaveImageRepository
    ): CreateTempPathForImage{
        return CreateTempPathForImage(repository)
    }


    @Provides
    @Singleton
    fun provideToShowNotification(
        repository: TrackerRepository,
        @Named("notificationPermission") notificationPermission : Boolean
    ): ToShowNotification {
        return  ToShowNotification(repository, notificationPermission)
    }

    @Provides
    @Singleton
    fun provideSchedulingHabitAlarm(
        scheduler: HabitAlarmScheduler,
        preferences: Preferences
    ) : SchedulingHabitAlarm {
        return SchedulingHabitAlarm(scheduler,preferences)
    }

    @Provides
    @Named("notificationPermission")
    fun provideNotificationPermission(
        @ApplicationContext context: Context
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

}