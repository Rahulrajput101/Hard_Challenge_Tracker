package com.ondevop.core_data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import com.ondevop.core_domain.MyNotification
import com.ondevop.core_domain.MyNotificationChannel
import com.ondevop.core_domain.MyNotificationManager
import kotlin.contracts.contract

class AndroidNotificationManagerImp(
   private val context: Context
) : MyNotificationManager {

    private val myNotificationManager = context.getSystemService(NotificationManager::class.java)
    override fun notify(myNotification: MyNotification) {
        Log.d("HAR","3")
        val activityIntent = Intent(context, Class.forName("com.ondevop.a75hard.MainActivity"))
        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0,
              activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = when(myNotification){
             MyNotification.HabitNotification -> {
               Notification.Builder(context , myNotification.channel.id)
                   .setContentTitle("Reminder")
                   .setContentText("It's time to complete your daily task")
                   .setContentIntent(resultPendingIntent)
                   .setAutoCancel(true)
                   .setSmallIcon(Icon.createWithResource(context, com.ondevop.core_domain.R.drawable.baseline_access_alarm_24))
                   .build()
            }
        }
        myNotificationManager.notify(myNotification.notificationId, notification)

    }

    override fun createChannel(channel: MyNotificationChannel) {
        when(channel){
            MyNotificationChannel.HabitReminderChannel -> {
                val notificationChannel = NotificationChannel( channel.id ,channel.channelName ,NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.description = "Used for habit tracking"

                myNotificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }
}