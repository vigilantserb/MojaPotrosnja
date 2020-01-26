package com.example.mojapotrosnja.utils

import android.os.Environment
import android.util.Log
import com.example.mojapotrosnja.models.FuelLog
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CsvFileManager {

    private val TAG = CsvFileManager::class.java.canonicalName

    fun addFuelLog(fuelLog: FuelLog) {
        val exportDir = File(Environment.getExternalStorageDirectory().path)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val file = File(exportDir, "FuelLogs.csv")


        try {
            if (!file.exists())
                file.createNewFile()
        } catch (e: IOException) {
            Log.e(TAG, "An error occured while creating a CSV file: ", e)
        }
        val csvWriter = CSVWriter(FileWriter(file))

        val column = arrayOf(
            "carData",
            "fuelAmount",
            "fuelPrice",
            "fuelDate"
        )
        csvWriter.writeNext(column)

        val array = arrayOf(
            fuelLog.carData,
            fuelLog.fuelAmount,
            fuelLog.fuelPrice,
            fuelLog.fuelDate
        )
        csvWriter.writeNext(array)
        csvWriter.close()
    }
}