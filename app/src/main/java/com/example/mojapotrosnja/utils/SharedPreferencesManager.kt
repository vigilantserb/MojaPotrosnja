package com.example.mojapotrosnja.utils

import android.content.SharedPreferences
import com.example.mojapotrosnja.models.Car
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPreferencesManager(
    private val sharedPreferences: SharedPreferences
) {

    private val CAR_TAG: String = "1"

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    var gson = Gson()

    fun addNewCarToList(car: Car): ArrayList<Car> {
        val carList = getCarList()
        carList.add(car)
        val json = gson.toJson(carList)
        editor.putString(CAR_TAG, json)
        editor.apply()
        return getCarList()
    }

    private fun getCarList(): ArrayList<Car> {
        val json = sharedPreferences.getString(CAR_TAG, null)
        val type = object : TypeToken<ArrayList<Car>>() {}.type
        return gson.fromJson(json, type) as? ArrayList<Car> ?: return ArrayList()
    }
}