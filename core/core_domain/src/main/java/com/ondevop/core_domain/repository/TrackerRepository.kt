package com.ondevop.core_domain.repository

import com.ondevop.core_domain.model.TrackedChallenge
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun upsertTrackedChallenge(trackedChallenge: TrackedChallenge) : Long

    fun getTrackedDataForDate(date: LocalDate): Flow<TrackedChallenge?>

    fun getAllTrackedChallenge() : Flow<List<TrackedChallenge>>

    suspend fun clearAllTrackedChallenge()

}