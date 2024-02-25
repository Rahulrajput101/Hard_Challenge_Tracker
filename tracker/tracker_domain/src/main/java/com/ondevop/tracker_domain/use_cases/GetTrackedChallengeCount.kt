package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow

class GetTrackedChallengeCount(
    private val repository: TrackerRepository
){
    operator fun invoke(): Flow<Int> {
        return repository.getTrackedChallengeCount()
    }
}