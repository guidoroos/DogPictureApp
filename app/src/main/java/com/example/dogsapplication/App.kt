package com.example.dogsapplication

import android.app.Application
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

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule, databaseModule, apiModule)
        }
    }
}


val appModule = module {

    single { DogRepository(api = get(), database = get()) }

    viewModel{ DogViewModel(get()) }
    viewModel{ DogHistoryViewModel(get()) }

}