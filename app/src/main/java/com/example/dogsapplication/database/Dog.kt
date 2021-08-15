package com.example.dogsapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_table")
data class Dog  (
    @PrimaryKey(autoGenerate = true)
    var dogId: Long = 0L,
    var breed: String,
    var url: String,
    var date: String)