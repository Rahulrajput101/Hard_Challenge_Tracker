package com.ondevop.tracker_domain.use_cases

import com.ondevop.core.domain.model.TrackedChallenge
import java.time.LocalDate

class FilterADayChallenge {
    operator fun invoke(date: LocalDate, allChallengeData: List<TrackedChallenge>): TrackedChallenge? {

        return allChallengeData.find { trackedChallenge ->
            trackedChallenge.date == date
        }
    }
}