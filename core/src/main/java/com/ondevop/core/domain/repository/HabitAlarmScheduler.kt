package com.ondevop.core.domain.repository

import com.ondevop.core.domain.model.HabitReminder

interface HabitAlarmScheduler {
    fun schedule(habitReminder: HabitReminder)
    fun cancel(habitReminder: HabitReminder)
}