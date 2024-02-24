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
     * This function is responsible for checking if the user has tracked any challenges and
     * performing a specific action based on certain conditions.
     */
    suspend operator fun invoke() {
        val twoDaysBeforeYesterday = LocalDate.now().minusDays(2)

        repository.getAllTrackedChallenge().collectLatest { challenges ->
            if (challenges.isNotEmpty()) {
                val lastChallengeDate = challenges.last().date
                // Check if the date of the last tracked challenge is before two days before yesterday
                if(lastChallengeDate.isBefore(twoDaysBeforeYesterday)){
                    repository.clearAllTrackedChallenge()
                }
            }
        }
    }
}
//
//Log.d("HasUser", "run1")
//val twoDaysBeforeYesterday = LocalDate.now().minusDays(2)
//
//repository.getAllTrackedChallenge().collectLatest { challenges ->
//    Log.d("HasUser", " 1$challenges")
//    if (challenges.isNotEmpty()) {
//        val lastChallengeDate = challenges.last().date
//        if (challenges.none {
//                it.date == LocalDate.now().minusDays(1)
//            } && challenges.none { it.date == twoDaysBeforeYesterday } && (twoDaysBeforeYesterday.isAfter(
//                lastChallengeDate
//            ))) {
//
//            // repository.clearAllTrackedChallenge()
//        }
//    }
//}