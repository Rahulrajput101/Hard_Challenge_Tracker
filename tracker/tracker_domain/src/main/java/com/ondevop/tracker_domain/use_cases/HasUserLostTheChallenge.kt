package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HasUserLostTheChallenge(
    private val repository: TrackerRepository
) {
    /**
     * Executes the use case logic. If there is a tracked challenge for the day before yesterday,
     * it is considered a loss, and all data in the database is cleared.
     */
    suspend operator fun invoke() {
        val twoDaysBeforeYesterday = LocalDate.now().minusDays(3)

        repository.getAllTrackedChallenge().map { challenges ->
            if (challenges.isNotEmpty() && challenges.last().date == twoDaysBeforeYesterday) {
                repository.clearAllTrackedChallenge()
            }
        }
    }

}