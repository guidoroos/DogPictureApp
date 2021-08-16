package com.example.dogsapplication.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.dogsapplication.MainActivity
import com.example.dogsapplication.R

// Notification ID.
private val CHANNEL_ID = "100"
private val CHANNEL_NAME = "dog_notifications"
private val NOTIFICATION_ID = 0

fun createChannel( context: Context) {
    //setup channel settings
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationChannel.apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "Dog Picture Notifications"
        }
        //create channel
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(notificationChannel)

    }
}


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        CHANNEL_ID
    )
        //minimum data need to be set for notification
        .setSmallIcon(R.drawable.dog)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)


    // send notification
    notify(NOTIFICATION_ID, builder.build())

}