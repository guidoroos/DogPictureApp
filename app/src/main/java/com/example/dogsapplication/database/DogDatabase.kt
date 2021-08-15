package com.example.dogsapplication.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@Database(entities = [Dog::class], version =1, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {

    abstract val dogDao:DogDao

}

val databaseModule = module {

    fun provideDatabase (application: Application) =
    Room.databaseBuilder(
        application,
        DogDatabase::class.java,
        "dog_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    fun provideDao (database: DogDatabase) =
        database.dogDao

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}
