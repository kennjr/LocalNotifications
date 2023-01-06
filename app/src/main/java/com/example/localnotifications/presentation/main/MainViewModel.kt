package com.example.localnotifications.presentation.main

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localnotifications.common.Constants
import com.example.localnotifications.di.BasicNotificationBuilder
import com.example.localnotifications.di.ProgressNotificationBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    @BasicNotificationBuilder val notificationBuilder: NotificationCompat.Builder,
    @ProgressNotificationBuilder val progressNotificationBuilder: NotificationCompat.Builder,
    val notificationManager: NotificationManagerCompat
        ) : ViewModel() {


    fun showSimpleNotification(){
        notificationManager.notify(Constants.BASIC_NOTIFICATION_ID, notificationBuilder.build())
    }

    fun updateNotification(){
        /**
         * NOTE : that the ID has to match the original notification,
         * otherwise the system will create a new notification within the same channel
         */
        notificationManager.notify(Constants.BASIC_NOTIFICATION_ID,
            notificationBuilder.setContentTitle("Updated Title").build())
    }

    fun removeNotification(){
        notificationManager.cancel(Constants.BASIC_NOTIFICATION_ID)
    }

    fun showProgressNotification(){
        // this is the max value for the progressbar
        val max = 10
        // this is for the current iteration
        var progress = 0
        viewModelScope.launch {
            // the while loop will run 10 times
            while (progress < max){
                // since this is for testing purposes we set the delay to simulate sth happening
                delay(1000)
                progress += 1
                notificationManager.notify(Constants.PROGRESS_NOTIFICATION_ID,
                    progressNotificationBuilder
                        .setContentTitle("Downloading")
                        .setContentText("$progress/$max")
                        .setProgress(max, progress, false)
                        .build())
            }
            notificationManager.notify(Constants.PROGRESS_NOTIFICATION_ID,
                // we're using the first builder bc we want to trigger the notification tone when the process is complete
                notificationBuilder
                    .setContentTitle("Download complete")
                    .setContentText("$progress/$max")
                        // we set the intent to null so that if the user clicks the notification it simply disappears
                    .setContentIntent(null)
                        // we clear the actions since the process is complete and there's nothing for the user to do
                    .clearActions()
//                    .setProgress(max, progress, false)
                    .setProgress(0,0,false)
                    .build())
        }

    }
}