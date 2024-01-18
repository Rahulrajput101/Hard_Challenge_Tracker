package com.ondevop.tracker_domain.use_cases


import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class IsYesterdayChallengeTracked(
    private val repository: TrackerRepository
) {

    operator fun invoke(): Flow<Boolean?> {
        return repository.getTrackedDataForDate(LocalDate.now().minusDays(1))
            .map { it != null }
    }
}