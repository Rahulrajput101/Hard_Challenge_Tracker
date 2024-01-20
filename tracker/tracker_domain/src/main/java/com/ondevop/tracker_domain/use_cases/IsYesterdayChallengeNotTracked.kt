package com.ondevop.tracker_domain.use_cases


import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class IsYesterdayChallengeNotTracked(
    private val repository: TrackerRepository
) {
    /**
     * Checks if there is at least one tracked challenge from yesterday in the repository.
     *
     * @return A [Flow] emitting a [Boolean] indicating whether yesterday's challenges are not tracked.
     */
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