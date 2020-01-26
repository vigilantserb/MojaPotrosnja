package com.example.mojapotrosnja.utils

import com.example.mojapotrosnja.common.BaseObservable
import com.example.mojapotrosnja.models.Car

class CarDataRepository(
   private val sharedPreferencesManager: SharedPreferencesManager
): BaseObservable<CarDataRepository.NewCarAddedListener>() {

    interface NewCarAddedListener {
        fun onNewCarAdded(carList: ArrayList<Car>)
    }

    fun addNewCar(car: Car){
        notifyListeners(sharedPreferencesManager.addNewCarToList(car))
    }

    private fun notifyListeners(carList: ArrayList<Car>) {
        listeners.forEach {
            it.onNewCarAdded(carList)
        }
    }
}