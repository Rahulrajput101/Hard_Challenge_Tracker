package com.ondevop.tracker_domain.use_cases

import android.util.Log
import com.ondevop.core_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HasUserLostTheChallenge(
    private val repository: TrackerRepository
) {

    /**
     * Checks if there are no challenges recorded on both yesterday's date and two days before yesterday's date,
     * and if two days before yesterday is after the date of the last recorded challenge.
     * If all conditions are met, it indicates a gap of at least two consecutive days without recorded challenges,
     * and executes the code inside the block to clear all tracked challenges.
     */
    suspend operator fun invoke() {
        Log.d("HasUser", "run1")
        val twoDaysBeforeYesterday = LocalDate.now().minusDays(2)

        repository.getAllTrackedChallenge().collectLatest { challenges ->
            Log.d("HasUser", " 1$challenges")
            if (challenges.isNotEmpty()) {
                val lastChallengeDate = challenges.last().date
                if (challenges.none {
                        it.date == LocalDate.now().minusDays(1)
                    } && challenges.none { it.date == twoDaysBeforeYesterday } && (twoDaysBeforeYesterday.isAfter(
                        lastChallengeDate
                    ))) {
                    Log.d("HasUser", "deleting the data")
                    Log.d("HasUser", "Current date: ${LocalDate.now()}")
                    Log.d("HasUser", "Two days before yesterday: $twoDaysBeforeYesterday")
                    Log.d("HasUser", "Challenge dates: ${challenges.map { it.date }}")
                    // repository.clearAllTrackedChallenge()
                }
            }
        }
    }

}