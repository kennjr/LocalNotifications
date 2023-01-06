package com.example.localnotifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.localnotifications.common.Constants
import com.example.localnotifications.di.BasicNotificationBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// we're annotating the receiver with the annotation below bc we need to inject the notificationManager and the builder
@AndroidEntryPoint
class ReplyReceiver: BroadcastReceiver() {

    // the injection method below is called field injection, the other one(used in VMs) is called constructor injection
    @Inject
    @BasicNotificationBuilder
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    // this method will get triggered the moment our receiver gets the intent
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null){
            // the remoteInput will contain the message typed in by the user(in the notification edt)
            val remoteInput = RemoteInput.getResultsFromIntent(intent)
            if (remoteInput != null){
                val input = remoteInput.getCharSequence(Constants.NOTIFICATION_MESSAGE_INPUT_RESULT_KEY).toString()
                Toast.makeText(context, "The reply $input", Toast.LENGTH_SHORT).show()
                // this person obj is what we'll use to show the user's response, gotten from the remoteInput
                val person = Person.Builder().setName("Me").build()
                // the message is an obj that contains the response and will be added to the notification
                val message = NotificationCompat.MessagingStyle.Message(input, System.currentTimeMillis(), person)
                val notificationStyle = NotificationCompat.MessagingStyle(person).addMessage(message)
                // we have to update the notification, if we don't do what we're doing below then the
                // input from the user will never get sent, there'll be an infinite loop
                notificationManager.notify(Constants.BASIC_NOTIFICATION_ID,
                    notificationBuilder
//                        .setStyle(notificationStyle)
                            // we set the style to null so that the input edt can disappear
                        .setStyle(null)
                        .build()
                )

            }
        }
    }


}