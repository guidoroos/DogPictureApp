package com.example.dogsapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Insert
    suspend fun insert(dog:Dog)

    @Delete
    suspend fun delete (dog:Dog)

    @Query("SELECT * FROM dog_table WHERE dogId = :dogId")
    suspend fun getDogById(dogId:Long): Dog

    @Query("SELECT * FROM dog_table ORDER BY dogId DESC LIMIT 1 ")
    suspend fun getLatestDog(): Dog

    @Query("DELETE FROM dog_table")
    suspend fun clear()

    @Query("SELECT * FROM dog_table ORDER BY date DESC")
    fun getAllDogs(): LiveData<List<Dog>>
}