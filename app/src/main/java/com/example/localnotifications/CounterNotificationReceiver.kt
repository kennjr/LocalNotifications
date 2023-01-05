package com.example.localnotifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * NOTE : Every receiver has to be registered in the manifest file
 */
class CounterNotificationReceiver: BroadcastReceiver() {

    // this method will get triggered the moment our receiver gets the intent
    override fun onReceive(context: Context, intent: Intent?) {
        // this is where we're gonna increase the val. of our counter and update our notification

        // the var. below is what we use to show and update the notification
        val service = CounterNotificationService(context)
        service.showNotification(++Counter.value)
    }
}