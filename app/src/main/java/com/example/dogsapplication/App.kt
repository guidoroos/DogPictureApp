package com.example.dogsapplication

import android.app.Application
import androidx.work.*
import com.example.dogsapplication.api.apiModule
import com.example.dogsapplication.database.databaseModule
import com.example.dogsapplication.doghistory.DogHistoryViewModel
import com.example.dogsapplication.dogpicture.DogRepository
import com.example.dogsapplication.dogpicture.DogViewModel
import com.example.dogsapplication.notifications.NotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

const val WORK_NAME = "daily_notification"

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule, databaseModule, apiModule)
        }

        setupRecurringWork()
    }

    private fun setupRecurringWork() {

        //set constraint for wifi only and not low battery
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()


        //set schedule and workertask using worker class name
        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()


        //keep existing work (otherwise replace), specify unique name
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}

val appModule = module {

    single { DogRepository(api = get(), database = get()) }

    viewModel{ DogViewModel(get()) }
    viewModel{ DogHistoryViewModel(get()) }

}