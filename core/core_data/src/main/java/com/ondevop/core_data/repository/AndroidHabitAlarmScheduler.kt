package com.ondevop.core_data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ondevop.core_data.receivers.HabitAlarmReceiver
import com.ondevop.core_domain.model.HabitReminder
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AndroidHabitAlarmScheduler(
    private val context : Context
) : HabitAlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(habitReminder: HabitReminder) {

        //set the reminder time to 10 pm
        val reminderTime = LocalTime.of(22,0)

        // Combine the reminder time with the date of the habitReminder
        val scheduledTime = LocalDateTime.of(habitReminder.time.toLocalDate(), reminderTime)


        if (scheduledTime.isBefore(LocalDateTime.now())) {
            scheduledTime.plusDays(1)
        }


        val intent = Intent(context, HabitAlarmReceiver::class.java)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            scheduledTime.atZone(ZoneId.systemDefault()).toEpochSecond()* 1000,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                habitReminder.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(habitReminder: HabitReminder) {
        TODO("Not yet implemented")
    }

    companion object{
        const val INTENT_HABIT_REMINDER_KEY = "intent_habit_reminder"
    }
}