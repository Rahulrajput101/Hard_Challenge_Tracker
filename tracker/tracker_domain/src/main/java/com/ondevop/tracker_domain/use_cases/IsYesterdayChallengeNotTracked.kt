package com.ondevop.tracker_domain.use_cases


import android.util.Log
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
        return  repository.getAllTrackedChallenge().map { challenges ->
            if (challenges.isNotEmpty()) {
                Log.d("Check","no empty ")
                val lastChallengeDate = challenges.first().date
                Log.d("Check","last challenge date :  $lastChallengeDate  day ${lastChallengeDate.dayOfMonth} and ${lastChallengeDate.dayOfYear} ")
                //This checks if the last challenge date is before yesterday's date.
                val v = lastChallengeDate.isBefore(LocalDate.now().minusDays(1))
                Log.d("Check"," v : $v ")
                    v
                // This expression checks if there are no challenges with yesterday's date.
                //challenges.none { it.date == LocalDate.now().minusDays(1) }

            } else {
                false
            }
        }
    }
}