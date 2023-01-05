package com.example.localnotifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

// this is the class that we'll use to display the notifications
class CounterNotificationService (val context: Context): CounterNotification {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // we should create an interface that'll handle this for better abstraction,
    // but this being a simple test we'll use a good-old fun.


    companion object{
        // this id has to be unique for each and every notification that u've got in ur app
        const val COUNTER_NOTIFICATION_ID = 1001
        // we're gonna use the const below to identify the channel we'd like to use for this
        // specific notification
        const val COUNTER_CHANNEL_ID = "counter_channel_id"
        // this is the name of the notification that the user will see in the app info screen(notifications fragment)
        const val COUNTER_CHANNEL_NAME = "Counter"
        // the importance var. will tell the OS how to position our notification(in terms of priority)
        // in the list of notifications
        @RequiresApi(Build.VERSION_CODES.N)
        const val NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
        const val COUNTER_NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 101
        const val COUNTER_INCREMENT_PENDING_INTENT_REQUEST_CODE = 102
    }

    override fun showNotification(counter: Int) {
        val incrementIntent = Intent(context, CounterNotificationReceiver::class.java)
        val activityIntent = Intent(context, MainActivity::class.java)
        // the flags will define wht should be done with the pending intent
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            // this flag will make sure that the receiver of the intent doesn't change anything abt it, since it's immutable
            PendingIntent.FLAG_IMMUTABLE
        else
            0
        // the pending intent below is what will allow the notificationManager(another app) to
        // run some code that's related to our application, like starting an activity
        val activityPendingIntent = PendingIntent.getActivity(context,
            COUNTER_NOTIFICATION_PENDING_INTENT_REQUEST_CODE, activityIntent, pendingIntentFlags)

        val counterIncrementPendingIntent = PendingIntent.getBroadcast(context,
            COUNTER_INCREMENT_PENDING_INTENT_REQUEST_CODE, incrementIntent, pendingIntentFlags)

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                // the smallIcon is necessary for any notification that u create,
            // it's what the user sees on the statusBar
            .setSmallIcon(R.drawable.edit_note)
                // as the setter implies, the line below will add a title to our notification
            .setContentTitle(context.getString(R.string.counter_notification_title))
                // this will describe what the notification is all abt,
            // giving the user relevant info abt the prompt that he's being notified of
            .setContentText(context.getString(R.string.counter_notification_text, counter))
                // the contentIntent will give the notification an onclick,
            // which in our case will open the MainActivity
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.ic_round_gesture, context.getString(R.string.increment), counterIncrementPendingIntent)
            .setOngoing(true).build()

        // the line below is what will run the notification
        notificationManager.notify(COUNTER_NOTIFICATION_ID, notification)
    }

}