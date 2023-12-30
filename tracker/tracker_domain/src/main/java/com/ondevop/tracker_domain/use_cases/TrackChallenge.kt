package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.repository.TrackerRepository

class TrackChallenge(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackedChallenge: TrackedChallenge
    ){
        repository.upsertTrackedChallenge(trackedChallenge)
    }
}