package com.example.dogsapplication.dogpicture

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import com.example.dogsapplication.api.DogApiService
import com.example.dogsapplication.api.DogResponse
import com.example.dogsapplication.database.Dog
import com.example.dogsapplication.database.DogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate.now
import java.util.*

/**
 * Dog repository
 *
 * @property database
 * @property api
 * @constructor Create empty Dog repository
 */
class DogRepository(private val database:DogDatabase, private val api:DogApiService) {
    suspend fun getNewDogAndSaveToDb() {

        withContext(Dispatchers.IO) {
            //get network data, wait until finished, then insert in db
            val dogResponse = api.getRandomDog()

            val newDog = createNewDogFromResponse(dogResponse)
            database.dogDao.insert(newDog)
        }
    }

    suspend fun getDogById (dogId:Long) :Dog {
        return database.dogDao.getDogById(dogId)
    }

    fun getAllDogs():LiveData<List<Dog>> {
        return database.dogDao.getAllDogs()
    }


    /**
     * Get latest dog added to database
     *
     * @return
     */
    suspend fun getLatestDog ():Dog {
        return database.dogDao.getLatestDog()
    }

    suspend fun deleteDog (dog:Dog) {
        database.dogDao.delete(dog)
    }

    /**
     * Create new dog from dog response, so that it can be saved to the database table
     *
     * @param dogResponse
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private fun createNewDogFromResponse(dogResponse: DogResponse): Dog {
        val message = dogResponse.message
        val breed = message.split("/")[4]

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy");
        val date = dateFormat.format(calendar.time);

        return Dog(
            0,
            breed,
            message,
            date
        )
    }
}