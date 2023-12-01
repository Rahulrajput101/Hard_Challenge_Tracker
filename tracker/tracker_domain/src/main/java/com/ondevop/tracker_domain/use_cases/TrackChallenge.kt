package com.ondevop.tracker_domain.use_cases

import com.ondevop.tracker_domain.model.TrackedChallenge
import com.ondevop.tracker_domain.repository.TrackerRepository

class TrackChallenge(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackedChallenge: TrackedChallenge
    ){
        repository.upsertTrackedChallenge(trackedChallenge)
    }
}