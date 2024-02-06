package com.ondevop.tracker_domain.use_cases

import android.util.Log
import java.time.LocalDate

class CheckTheDateIsInRange(

) {
    operator fun invoke(days: Int, currentDate: LocalDate): Result<Unit> {
        return try {
            if (currentDate.minusDays(1) > LocalDate.now().minusDays(days.toLong())) {
                Result.success(Unit)
            } else {
                Result.failure(CannotNavigateException)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}

object CannotNavigateException : Exception()