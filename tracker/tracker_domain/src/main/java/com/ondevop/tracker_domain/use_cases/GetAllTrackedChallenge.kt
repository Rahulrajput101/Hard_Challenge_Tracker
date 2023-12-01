package com.ondevop.tracker_domain.use_cases

import com.ondevop.tracker_domain.model.TrackedChallenge
import com.ondevop.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow

class GetAllTrackedChallenge(
    private val repository: TrackerRepository
) {
     operator fun invoke() : Flow<List<TrackedChallenge>> {
        return repository.getAllTrackedChallenge()
    }
}