package com.ondevop.core_domain.use_cases


import com.ondevop.core_domain.model.HabitReminder
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import java.time.LocalDateTime
import javax.inject.Inject

class SchedulingHabitAlarm @Inject constructor(
    private val habitAlarmScheduler: HabitAlarmScheduler
) {

    operator fun invoke(){
        habitAlarmScheduler.schedule(HabitReminder(LocalDateTime.now()))
    }

}