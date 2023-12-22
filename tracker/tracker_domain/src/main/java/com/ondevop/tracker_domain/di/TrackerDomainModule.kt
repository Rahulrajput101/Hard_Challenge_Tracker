package com.ondevop.tracker_domain.di

import com.ondevop.core.domain.repository.TrackerRepository
import com.ondevop.tracker_domain.use_cases.FilterADayChallenge
import com.ondevop.tracker_domain.use_cases.GetAllTrackedChallenge
import com.ondevop.tracker_domain.use_cases.GetTrackedDataForDate
import com.ondevop.tracker_domain.use_cases.TrackChallenge
import com.ondevop.tracker_domain.use_cases.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
    ): TrackerUseCases {

        return TrackerUseCases(
            trackChallenge = TrackChallenge(repository),
            getTrackedDataForDate = GetTrackedDataForDate(repository),
            getAllTrackedChallenge = GetAllTrackedChallenge(repository),
            filterADayChallenge = FilterADayChallenge()
        )
    }
}