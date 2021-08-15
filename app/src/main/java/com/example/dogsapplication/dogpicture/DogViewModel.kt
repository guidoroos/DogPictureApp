package com.example.dogsapplication.dogpicture

import androidx.lifecycle.*
import com.example.dogsapplication.database.Dog
import kotlinx.coroutines.launch

class DogViewModel(val repository: DogRepository): ViewModel() {
    private val _currentDog= MutableLiveData<Dog>()
    val currentDog: LiveData<Dog>
    get() = _currentDog

    fun getNewDog () {
        viewModelScope.launch {
            repository.getNewDogAndSaveToDb()
            getLatestDog()

        }
    }

    private fun getLatestDog(){
        viewModelScope.launch {
            _currentDog.value = repository.getLatestDog()
        }
    }



    fun updateCurrentDog (dogId:Long) {
        viewModelScope.launch {
            _currentDog.value = repository.getDogById(dogId)
        }
    }



}

