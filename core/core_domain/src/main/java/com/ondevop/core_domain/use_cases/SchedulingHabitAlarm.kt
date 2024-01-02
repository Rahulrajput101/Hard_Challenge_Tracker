package com.ondevop.core_domain.use_cases


import android.util.Log
import com.ondevop.core_domain.model.HabitReminder
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import javax.inject.Inject

class SchedulingHabitAlarm @Inject constructor(
    private val habitAlarmScheduler: HabitAlarmScheduler,
    private val preferences: Preferences
) {

    suspend operator fun invoke(){
        habitAlarmScheduler.schedule(HabitReminder(LocalDateTime.now())).onSuccess {
            Log.d("ma"," alarm = true")
            preferences.saveAlarmSchedule(true)
        }
            .onFailure {
                it.printStackTrace()
            }
    }

}