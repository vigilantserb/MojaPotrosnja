package com.example.mojapotrosnja.utils

import android.os.Environment
import android.util.Log
import com.example.mojapotrosnja.models.FuelLog
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CsvFileManager {

    private val TAG = CsvFileManager::class.java.canonicalName

    fun addFuelLog(fuelLog: FuelLog) {
        val file = createCsvFile()
        val csvWriter = CSVWriter(FileWriter(file, true))

        if(!isFileAlreadyWritten(file)){
            val column = arrayOf(
                "carData",
                "fuelAmount",
                "fuelPrice",
                "fuelDate"
            )
            csvWriter.writeNext(column)
        }

        val array = arrayOf(
            fuelLog.carData,
            fuelLog.fuelAmount,
            fuelLog.fuelPrice,
            fuelLog.fuelDate
        )
        csvWriter.writeNext(array)
        csvWriter.close()
    }

    private fun createCsvFile(): File {
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
        return file
    }

    private fun isFileAlreadyWritten(file: File): Boolean {
        try {
            val csvReader = CSVReader(FileReader(file))
            val nextRecord = csvReader.readNext()
            while (nextRecord != null) {
                return nextRecord[0].contains("carData")
            }
        } catch (e: java.lang.Exception) {
            println("An error occured while reading the CSV file.")
        }
        return false
    }
}