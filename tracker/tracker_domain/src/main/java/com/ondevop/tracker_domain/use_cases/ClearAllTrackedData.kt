package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.repository.TrackerRepository


class ClearAllTrackedData(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke() {
        repository.clearAllTrackedChallenge()
    }
}