package com.ondevop.tracker_domain.repository

import com.ondevop.tracker_domain.model.TrackedChallenge
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {

    suspend fun upsertTrackedChallenge(trackedChallenge: TrackedChallenge) : Long

    fun getAllTrackedChallenge() : Flow<List<TrackedChallenge>>

    suspend fun clearAllTrackedChallenge()
}