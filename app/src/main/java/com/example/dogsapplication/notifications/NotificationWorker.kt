package com.example.dogsapplication.notifications

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dogsapplication.R


class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        return try {
            createChannel(appContext)

            val notificationManager = ContextCompat.getSystemService(
                appContext,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendNotification(appContext.getString(R.string.notification_message),applicationContext)

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(NotificationWorker::class.simpleName, throwable.stackTraceToString())
            Result.failure()
        }
    }
}