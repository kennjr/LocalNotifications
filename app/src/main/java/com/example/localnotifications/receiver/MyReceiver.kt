package com.example.localnotifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.localnotifications.common.Constants.BASIC_NOTIFICATION_INTENT_EXTRA_KEY

class MyReceiver: BroadcastReceiver() {

    // this method will get triggered the moment our receiver gets the intent
    override fun onReceive(context: Context, intent: Intent?) {
        // this is where we're gonna increase the val. of our counter and update our notification

        val message = intent?.getStringExtra(BASIC_NOTIFICATION_INTENT_EXTRA_KEY)
        if (message != null){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
//        val service = CounterNotificationService(context)
//        service.showNotification(++Counter.value)
    }


}