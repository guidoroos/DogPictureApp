package com.example.dogsapplication

import android.app.Application
import androidx.work.*
import com.example.dogsapplication.api.apiModule
import com.example.dogsapplication.database.databaseModule
import com.example.dogsapplication.doghistory.DogHistoryViewModel
import com.example.dogsapplication.dogpicture.DogRepository
import com.example.dogsapplication.dogpicture.DogViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

const val WORK_NAME = "daily_notification"

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin for dependency injection
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule, databaseModule, apiModule)
        }

    }

}

/**
 * App module for dependency injection in whole app scope
 */
val appModule = module {

    single { DogRepository(api = get(), database = get()) }
    viewModel{ DogViewModel(get()) }
    viewModel{ DogHistoryViewModel(get()) }

}