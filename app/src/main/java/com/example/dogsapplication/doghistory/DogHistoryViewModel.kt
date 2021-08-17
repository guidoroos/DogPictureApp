package com.example.dogsapplication.doghistory

import androidx.lifecycle.*
import com.example.dogsapplication.database.Dog
import com.example.dogsapplication.dogpicture.DogRepository
import kotlinx.coroutines.launch


class DogHistoryViewModel(private val repository: DogRepository): ViewModel() {

    init {
        refreshDogs()
    }

    /**
     * Dog list used for recyclerview items data
     */
    lateinit var dogList:LiveData<List<Dog>>

    /**
     * make sure that when a new dog is created, it is added to the recyclerview layout
     *
     */
    private fun refreshDogs () {
        viewModelScope.launch {
            dogList = repository.getAllDogs()
        }
    }

    fun deleteDog (dog:Dog) {
        viewModelScope.launch {
            repository.deleteDog(dog)
        }
    }

}

