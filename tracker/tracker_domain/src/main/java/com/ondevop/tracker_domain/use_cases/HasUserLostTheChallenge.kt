package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.model.TrackedChallenge
import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
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
        val dayBeforeYesterday = LocalDate.now().minusDays(2)

             repository.getAllTrackedChallenge().map {challenges ->
            // Check if the last tracked challenge has the date of the day before yesterday
            if(challenges.isNotEmpty() && challenges.last().date == dayBeforeYesterday){
                repository.clearAllTrackedChallenge()
            }

        }
    }
}