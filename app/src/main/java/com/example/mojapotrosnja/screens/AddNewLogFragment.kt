package com.example.mojapotrosnja.screens


import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mojapotrosnja.R
import com.example.mojapotrosnja.common.text
import com.example.mojapotrosnja.common.toStringArray
import com.example.mojapotrosnja.models.Car
import com.example.mojapotrosnja.models.FuelLog
import com.example.mojapotrosnja.utils.CarDataRepository
import com.example.mojapotrosnja.utils.CsvFileManager
import com.example.mojapotrosnja.utils.PermissionManager
import com.example.mojapotrosnja.utils.SharedPreferencesManager
import kotlinx.android.synthetic.main.fragment_add_new_log.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AddNewLogFragment : Fragment(), CarDataRepository.NewCarAddedListener,
    PermissionManager.Listener, DatePickerDialog.OnDateSetListener {

    lateinit var carDataRepository: CarDataRepository
    lateinit var permissionManager: PermissionManager
    lateinit var csvFileManager: CsvFileManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_log, container, false)
    }

    override fun onStart() {
        super.onStart()
        carDataRepository.registerListener(this)
        permissionManager.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        carDataRepository.unregisterListener(this)
        permissionManager.unregisterListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carDataRepository = CarDataRepository(
            SharedPreferencesManager(
                context?.getSharedPreferences(
                    "myPrefs",
                    Context.MODE_PRIVATE
                )!!
            )
        )

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context!!,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        displaySpinnerValues(carDataRepository.getCurrentCars())
        permissionManager = PermissionManager(activity)
        csvFileManager = CsvFileManager()
        add_car.setOnClickListener {
            carDataRepository.addNewCar(Car("Volkswagen", "Golf MK4", "2003", "Diesel"))
        }

        add_log_btn.setOnClickListener {
            requestWritePermissions()
        }
        add_date.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun requestWritePermissions() {
        val permissionsArray = ArrayList<String>()
        permissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionManager.requestPermissions(permissionsArray)
    }

    override fun onNewCarAdded(carList: ArrayList<Car>) {
        displaySpinnerValues(carList)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pick_date_et.setText("$year-${month + 1}-$dayOfMonth")
    }

    private fun displaySpinnerValues(carList: ArrayList<Car>) {
        context?.also {
            val adapter = ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item, carList.toStringArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }
    }

    override fun permissionsGranted() {
        csvFileManager.addFuelLog(
            FuelLog(
                spinner1.selectedItem.toString(),
                fuel_amount_et.text(),
                fuel_price_et.text(),
                pick_date_et.text()
            )
        )
    }

    override fun permissionNotGranted() {
        Toast.makeText(context, "Permission not granted. Try again...", Toast.LENGTH_SHORT).show()
    }
}
