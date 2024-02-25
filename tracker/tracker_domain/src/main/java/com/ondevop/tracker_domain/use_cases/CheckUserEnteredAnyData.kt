package com.ondevop.tracker_domain.use_cases

import com.ondevop.core_domain.repository.TrackerRepository
import com.ondevop.core_domain.uitl.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class CheckUserEnteredAnyData(
    private val repository: TrackerRepository
) {

    /**
     * Invokes the [CheckUserEnteredAnyData] use case, checking if the user has entered any data for the given [date].
     *
     * @param date The [LocalDate] for which data is to be checked.
     * @return A [Flow] emitting a [Boolean] value indicating whether the user has entered any data for the specified date.
     */
    operator fun invoke(date: LocalDate = LocalDate.now().minusDays(1)) : Flow<Boolean> {
        return repository.getTrackedDataForDate(date).map {trackedChallenge ->
            trackedChallenge?.let {
                it.read || it.waterIntake >= 0 ||
                        it.workedOut > 0 ||  !it.imageUri.isNullOrBlank()
            } ?: true
        }
    }
}