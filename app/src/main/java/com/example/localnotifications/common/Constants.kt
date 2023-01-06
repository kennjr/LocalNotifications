package com.example.localnotifications.common

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {

    // we're gonna use the const below to identify the channel we'd like to use for this
    // specific notification
    const val BASIC_NOTIFICATION_CHANNEL_ID = "basic_notification_id"
    // this id has to be unique for each and every notification that u've got in ur app
    const val BASIC_NOTIFICATION_ID = 1002
    // this is the name of the notification that the user will see in the app info screen(notifications fragment)
    const val BASIC_NOTIFICATION_CHANNEL_NAME = "Basic Notification"
    // the importance var. will tell the OS how to position our notification(in terms of priority)
    // in the list of notifications
    @RequiresApi(Build.VERSION_CODES.N)
    const val BASIC_NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT

    const val BASIC_NOTIFICATION_INTENT_EXTRA_KEY = "message_key"
    const val BASIC_NOTIFICATION_MESSAGE_ACTION_REQUEST_CODE = 103
    const val BASIC_NOTIFICATION_CLICK_ACTION_REQUEST_CODE = 104

    const val REPLY_INTENT_REQUEST_CODE = 105

    const val DETAILS_SCREEN_ARG = "details_argument"
    const val APP_URL = "https://ramanie.com"

    const val PROGRESS_NOTIFICATION_CHANNEL_ID = "progress_notification_id"
    const val PROGRESS_NOTIFICATION_ID = 1003
    const val PROGRESS_NOTIFICATION_CHANNEL_NAME = "Progress Notification"
    @RequiresApi(Build.VERSION_CODES.N)
    const val PROGRESS_NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_LOW

    const val NOTIFICATION_MESSAGE_INPUT_RESULT_KEY = "notification_message_input_key"
}