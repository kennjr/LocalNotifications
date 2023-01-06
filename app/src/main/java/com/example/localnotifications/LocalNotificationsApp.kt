package com.example.localnotifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp

// we need this class for us to create the notification channels for our diff. notifications
@HiltAndroidApp
class LocalNotificationsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // the fun below will create a notification channel that can be used to display a single notification
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // the code in the if is needed by devices of version O(oreo) and abv.
        // bfore the oreo version notification channel didn't exist, so showing notifications was a piece of cake
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CounterNotificationService.COUNTER_CHANNEL_ID,
                CounterNotificationService.COUNTER_CHANNEL_NAME,
                CounterNotificationService.NOTIFICATION_IMPORTANCE)

            // the description set below will be visible to the user and should give the user more
            // info abt the specific notification(it's advised that u use a res. string)
            channel.description = "Used for the increment counter notification"

            // the lines abv. have created the channel instance, below we're gonna create the actual notification
            // through the notificationManager
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}