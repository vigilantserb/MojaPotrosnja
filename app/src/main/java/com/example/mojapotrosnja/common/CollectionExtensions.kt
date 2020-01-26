package com.example.mojapotrosnja.common

import com.example.mojapotrosnja.models.Car

fun ArrayList<Car>.toStringArray(): ArrayList<String>{
    val stringCarList = ArrayList<String>(1)
    this.forEach {
        stringCarList.add(it.toString())
    }
    return stringCarList
}