package com.example.upauto.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.upauto.Car
import com.google.firebase.firestore.FirebaseFirestore

class Repo {
    fun getUserData():LiveData<MutableList<Car>>{
        val mutableData = MutableLiveData<MutableList<Car>>()
        FirebaseFirestore.getInstance().collection("Cars").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Car>()
            for(document in result) {
                val imageUrl = document.getString("imageUrl")
                val titulo = document.getString("titulo")
                val descripcion = document.getString("descripcion")
                val precio = document.getString("precio")








                val car = Car(imageUrl!!,titulo!!,descripcion!!,precio!!)
                listData.add(car)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}