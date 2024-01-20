package com.ondevop.tracker_domain.use_cases

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
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
        operator fun invoke() {
        val twoDaysBeforeYesterday = LocalDate.now().minusDays(2)

        repository.getAllTrackedChallenge().map { challenges ->
            if (challenges.size >= 2) {
                if (challenges.none {
                        it.date == LocalDate.now().minusDays(1)
                    } && challenges.none { it.date == twoDaysBeforeYesterday }) {
                    Log.d("HasUser", "deleting the data")
                     repository.clearAllTrackedChallenge()
                }
            }
        }
    }

}