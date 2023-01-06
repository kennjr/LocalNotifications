package com.example.localnotifications.di

import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.example.localnotifications.MainActivity
import com.example.localnotifications.R
import com.example.localnotifications.common.Constants
import com.example.localnotifications.receiver.MyReceiver
import com.example.localnotifications.receiver.ReplyReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * We'll use this module to inject the pieces needed to create and show notifications
 */

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Singleton
    @Provides
    // we're adding the annotation below so that we can be able to get this specific builder when
    // trying to inject the basic notification somewhere
    @BasicNotificationBuilder
    fun provideNotificationBuilder( @ApplicationContext context: Context):
            NotificationCompat.Builder{
        // the flags will define wht should be done with the pending intent
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        // this flag will make sure that the receiver of the intent doesn't change anything abt it, since it's immutable
            PendingIntent.FLAG_IMMUTABLE
        else
            0
        // the flags will define wht should be done with the pending intent
        val replyPendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            // this flag is what should be used if we're working with the direct reply
            PendingIntent.FLAG_MUTABLE
        else
            0

        // the var. below is for the edt that'll appear when the user wants to type sth in and send it to the app
        val remoteInput = androidx.core.app.RemoteInput.Builder(Constants.NOTIFICATION_MESSAGE_INPUT_RESULT_KEY)
            .setLabel(context.getString(R.string.type_msg))
            .setChoices(arrayOf("1st choice", "2nd choice"))
            .build()
        val replyIntent = Intent(context, ReplyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(context,
            Constants.REPLY_INTENT_REQUEST_CODE, replyIntent, replyPendingIntentFlags)

        // this is the intent that'll be triggered by the first action's onclick
        val intent = Intent(context, MyReceiver::class.java).apply {
            putExtra(
                // the key has to match the one we've got in our receiver
                Constants.BASIC_NOTIFICATION_INTENT_EXTRA_KEY, "The action btn has been clicked")
        }
        val pendingIntent = PendingIntent.getBroadcast(context,
            Constants.BASIC_NOTIFICATION_MESSAGE_ACTION_REQUEST_CODE, intent, pendingIntentFlags)

        /**
         * NOTE : the intent below will open the mainactivity of the app when the user clicks the notification
         *   val clickIntent = Intent(context, MainActivity::class.java)
         *   val clickPendingIntent = PendingIntent.getActivity(context,
         *   Constants.BASIC_NOTIFICATION_CLICK_ACTION_REQUEST_CODE, intent, pendingIntentFlags)
         */
        val intentArgumentText = "Coming from the notification"
        val clickIntent = Intent(
            // the action that should be triggered by the intent
            Intent.ACTION_VIEW,
            // this is the uri, which is the same as what we passed in the deeplinks param
            "${Constants.APP_URL}/${Constants.DETAILS_SCREEN_ARG}=$intentArgumentText".toUri(),
            context, MainActivity::class.java)
        // with the intent below we're creating a synthetic backstack(a fake backstack), we do this
        // so that once the activity is launched if the user presses the back btn they get navigated to
        // the entry that precedes the one opened by the notification, the fake backstack will allow the
        // user to navigate through the app as if he launched the app
        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            // we're using the app's stack (from the first screen) and navigating all the way to the screen we set in the intent
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(Constants.BASIC_NOTIFICATION_CLICK_ACTION_REQUEST_CODE, pendingIntentFlags)!!
        }

        val person = Person.Builder().setName("User One").build()
//        val notificationStyle = NotificationCompat.MessagingStyle().build()
        val notificationStyle = NotificationCompat.MessagingStyle(person).addMessage("Hello, User 2wo", System.currentTimeMillis(), person)

        val replyAction = NotificationCompat.Action.Builder(R.drawable.ic_round_gesture,
            context.getString(R.string.reply), replyPendingIntent)
            .addRemoteInput(remoteInput).build()

        // this is where we describe how our notification will look like
        // the channel Id will be unique for the basic notification
        return NotificationCompat.Builder(context, Constants.BASIC_NOTIFICATION_CHANNEL_ID)
            // the smallIcon is necessary for any notification that u create,
            // it's what the user sees on the statusBar
            .setSmallIcon(R.drawable.edit_note)
            // as the setter implies, the line below will add a title to our notification
            .setContentTitle(context.getString(R.string.basic_notification_title))
            // this will describe what the notification is all abt,
            // giving the user relevant info abt the prompt that he's being notified of
            .setContentText(context.getString(R.string.basic_notification_text))
            // the priority setter will tell the sys. how intrusive the notification should be,
            //this will be applicable to android devices running version 7.1 and below(25 -)
            // for newer devices we'll set the importance in the notification channel
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // the setter below will allow us to ctrl what notification info
            // is shown when the user's device is locked
            // PUBLIC - will show all of the notification's content if the screen is locked
            // PRIVATE - will show some of the info eg: title, smallIcon app name etc,
            //          - we'll have to create another version of the notification template that
            //          - will be shown when the screen is locked. We're gonna need another notificationBuilder for that
            // SECRET - will not show anything
            .setVisibility(VISIBILITY_PUBLIC)
                // the setter below is useful if we're using VISIBILITY_PRIVATE in teh visibility setter
            .setPublicVersion(
                NotificationCompat.Builder(context, Constants.BASIC_NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("The Public Title")
                    .setContentText("The pubilc text")
                    .setSmallIcon(R.drawable.edit_note).build()
            )
            .setStyle(notificationStyle)
                // the setter below will make sure that the user gets an alert the very first time
            // the notification appears in the drawer, from then on the tone won't be triggered
            .setOnlyAlertOnce(true)
//            .addAction(R.drawable.ic_round_gesture, "ACTION", pendingIntent)
            .addAction(replyAction)
            .setContentIntent(clickPendingIntent)
    }

    @Singleton
    @Provides
    // we're adding the annotation below so that we can be able to get this specific builder when
    // trying to inject the progress notification somewhere
    @ProgressNotificationBuilder
    fun provideProgressNotificationBuilder(@ApplicationContext context: Context):
            NotificationCompat.Builder{
        return NotificationCompat.Builder(context, Constants.PROGRESS_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_gesture)
            // since this is a builder for the progress notification we'll set a low priority
            .setPriority(NotificationCompat.PRIORITY_LOW)
            // the line below will prevent the user from swiping the notification if the process
            // indicated by the progressbar isn't complete
            .setOngoing(true)

    }

    @Singleton
    @Provides
    fun provideNotificationManager( @ApplicationContext context: Context):
            NotificationManagerCompat{
        // the line below will init the notification manager
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                // this id should match the ID we passed in the notificationBuilder
                Constants.BASIC_NOTIFICATION_CHANNEL_ID,
                Constants.BASIC_NOTIFICATION_CHANNEL_NAME,
                Constants.BASIC_NOTIFICATION_IMPORTANCE)

            // the description set below will be visible to the user and should give the user more
            // info abt the specific notification(it's advised that u use a res. string)
            channel.description = "This is a basic test notification that shows u some irrelevant information"

            // we're gonna create a new channel for the progress notification
            // we do this bc the main channel has a high importance lvl and we don't want to trigger
            // the notification tone on every iteration of the progress, so we'll give the new channel
            // a low importance lvl so as to prevent that from happening
            val progressChannel = NotificationChannel(
                // this id should match the ID we passed in the notificationBuilder
                Constants.PROGRESS_NOTIFICATION_CHANNEL_ID,
                Constants.PROGRESS_NOTIFICATION_CHANNEL_NAME,
                // we're giving the channel a low importance lvl to prevent the notification
                // tone from being triggered
                Constants.PROGRESS_NOTIFICATION_IMPORTANCE
            )
            // the description set below will be visible to the user and should give the user more
            // info abt the specific notification(it's advised that u use a res. string)
            progressChannel.description = "This is a basic progress notification that shows u some test progress information"

            // the lines abv. have created the channel instance, below we're gonna create the actual notification
            // through the notificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(progressChannel)
        }
        return notificationManager
    }

}