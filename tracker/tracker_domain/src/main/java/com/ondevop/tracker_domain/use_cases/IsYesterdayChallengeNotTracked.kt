package com.ondevop.tracker_domain.use_cases


import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class IsYesterdayChallengeNotTracked(
    private val repository: TrackerRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.getAllTrackedChallenge().map { challenges ->
            if (challenges.size > 1) {
                challenges.none { it.date == LocalDate.now().minusDays(1) }
            } else {
                false
            }
        }
    }
}