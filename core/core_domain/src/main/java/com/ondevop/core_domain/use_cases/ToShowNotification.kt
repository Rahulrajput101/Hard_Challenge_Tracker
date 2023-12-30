package com.ondevop.core_domain.use_cases

import com.ondevop.core_domain.repository.TrackerRepository
import com.ondevop.core_domain.uitl.Constant.DEFAULT_WATER_GOAL
import com.ondevop.core_domain.uitl.Constant.DEFAULT_WORKOUT_GOAL
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.singleOrNull
import java.time.LocalDate
import javax.inject.Inject

class ToShowNotification @Inject constructor(
   private val repository: TrackerRepository
) {
    suspend operator fun invoke(date: LocalDate) : Boolean {
        return repository.getTrackedDataForDate(date)
            .map { trackedChallenge ->
                trackedChallenge?.let {
                    !it.read && it.waterIntake != DEFAULT_WATER_GOAL &&
                            it.workedOut != DEFAULT_WORKOUT_GOAL&& it.imageUri != null
                } ?: false
            }
            .singleOrNull() ?: false
    }
}