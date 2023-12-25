package com.ondevop.core.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ondevop.core.data.local.dao.TrackerDao
import com.ondevop.core.domain.MyNotification
import com.ondevop.core.domain.MyNotificationManager
import com.ondevop.core.domain.repository.TrackerRepository
import com.ondevop.core.domain.use_cases.ToShowNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class HabitAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var toShowNotification: ToShowNotification

    @Inject
    lateinit var myNotificationManager: MyNotificationManager

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(p0: Context?, p1: Intent?) {
        GlobalScope.launch(Dispatchers.Main) {
            val shouldShowNotification = withContext(Dispatchers.IO){
                toShowNotification(LocalDate.now())
            }

            if(shouldShowNotification){
                myNotificationManager.notify(MyNotification.HabitNotification)
            }
        }

    }


}