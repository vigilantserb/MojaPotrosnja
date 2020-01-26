package com.example.mojapotrosnja.models

import java.io.Serializable

class Car(
    val carManufacturer: String,
    val carModel: String,
    val carYearMake: String,
    val carGasType: String
): Serializable {

    override fun toString(): String {
        return "$carManufacturer $carModel $carYearMake $carGasType"
    }
}