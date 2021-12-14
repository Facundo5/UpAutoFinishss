package com.example.upauto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upauto.Car
import com.example.upauto.domain.Repo

class HomeViewModel: ViewModel() {
    private val repo = Repo()
    fun fetchCarData():LiveData<MutableList<Car>>{
        val mutableData = MutableLiveData<MutableList<Car>>()
        repo.getUserData().observeForever { CarList ->
            mutableData.value = CarList
        }
        return mutableData
    }
}