package com.ondevop.core_domain.repository

import com.ondevop.core_domain.model.HabitReminder

interface HabitAlarmScheduler {
    fun schedule(habitReminder: HabitReminder) : Result<Unit>
    fun cancel(habitReminder: HabitReminder)
}