package com.ondevop.tracker_domain.use_cases

import com.ondevop.core.domain.model.TrackedChallenge
import com.ondevop.core.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow

class GetAllTrackedChallenge(
    private val repository: TrackerRepository
) {
     operator fun invoke() : Flow<List<TrackedChallenge>> {
        return repository.getAllTrackedChallenge()
    }
}