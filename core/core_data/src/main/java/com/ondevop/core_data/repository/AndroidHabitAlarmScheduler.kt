package com.ondevop.core_data.repository

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.ondevop.core_data.receivers.HabitAlarmReceiver
import com.ondevop.core_domain.model.HabitReminder
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.repository.HabitAlarmScheduler
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AndroidHabitAlarmScheduler(
    private val context : Context,
) : HabitAlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(habitReminder: HabitReminder) : Result<Unit> {

        //set the reminder time to 10 pm
        val reminderTime = LocalTime.of(22,0)

        // Combine the reminder time with the date of the habitReminder
        val scheduledTime = LocalDateTime.of(habitReminder.time.toLocalDate(), reminderTime)


        if (scheduledTime.isBefore(LocalDateTime.now())) {
            scheduledTime.plusDays(1)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure( Exception("Permission not allowed to show notification"))
        }


        val intent = Intent(context, HabitAlarmReceiver::class.java)
        alarmManager.setInexactRepeating(
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

         return Result.success(Unit)
    }

    override fun cancel(habitReminder: HabitReminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                habitReminder.hashCode(),
                Intent(context,HabitAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}