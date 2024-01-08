package com.ondevop.core_domain.use_cases

import android.util.Log
import com.ondevop.core_domain.repository.TrackerRepository
import com.ondevop.core_domain.uitl.Constant.DEFAULT_WATER_GOAL
import com.ondevop.core_domain.uitl.Constant.DEFAULT_WORKOUT_GOAL
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.singleOrNull
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

class ToShowNotification @Inject constructor(
   private val repository: TrackerRepository,
    @Named("notificationPermission") private val notificationPermission: Boolean
) {
    suspend operator fun invoke(date: LocalDate) : Boolean {
        /**
         * Checks whether a notification should be shown for a given date based on tracked data.
         *
         * @param date The date for which to check whether a notification should be shown.
         * @return `true` if a notification should be shown, `false` otherwise.
         */
       return try {
            repository.getTrackedDataForDate(date)
                .map { trackedChallenge ->
                    // Conditions for showing notification:
                    // 1. The challenge is not marked as read.
                    // 2. Water intake is not equal to the default goal.
                    // 3. Worked out is not equal to the default goal.
                    // 4. Image URI is null or blank.
                    trackedChallenge?.let {
                        !it.read || it.waterIntake != DEFAULT_WATER_GOAL ||
                                it.workedOut != DEFAULT_WORKOUT_GOAL ||  it.imageUri.isNullOrBlank()
                    } ?: true
                }
                .first()  && notificationPermission // Show notification only if permission is granted
        } catch (e: Exception) {
            // Handle exceptions, log, or return a default value
            Log.e("ToShowNotification", "Error getting tracked data", e)
            false
        }
    }
}