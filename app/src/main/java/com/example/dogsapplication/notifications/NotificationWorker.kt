package com.example.dogsapplication.notifications

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dogsapplication.R


//TODO: use workmanager to schedule a notification to be shown once per day,
// when on wifi and when not having low power
