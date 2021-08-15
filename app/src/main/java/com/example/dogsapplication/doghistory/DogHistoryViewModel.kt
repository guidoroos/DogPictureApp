package com.example.dogsapplication.doghistory

import androidx.lifecycle.*
import com.example.dogsapplication.database.Dog
import com.example.dogsapplication.dogpicture.DogRepository
import kotlinx.coroutines.launch


class DogHistoryViewModel(private val repository: DogRepository): ViewModel() {

    init {
        refreshDogs()
    }

    lateinit var dogList:LiveData<List<Dog>>


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

