package com.example.mojapotrosnja.screens


import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mojapotrosnja.R
import com.example.mojapotrosnja.common.text
import com.example.mojapotrosnja.common.toStringArray
import com.example.mojapotrosnja.models.Car
import com.example.mojapotrosnja.models.FuelLog
import com.example.mojapotrosnja.utils.CarDataRepository
import com.example.mojapotrosnja.utils.CsvFileManager
import com.example.mojapotrosnja.utils.RequestPermissionUseCase
import com.example.mojapotrosnja.utils.SharedPreferencesManager
import kotlinx.android.synthetic.main.fragment_add_new_log.*

/**
 * A simple [Fragment] subclass.
 */
class AddNewLogFragment : Fragment(), CarDataRepository.NewCarAddedListener,
    RequestPermissionUseCase.Listener {

    lateinit var carDataRepository: CarDataRepository
    lateinit var requestPermission: RequestPermissionUseCase
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
        requestPermission.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        carDataRepository.unregisterListener(this)
        requestPermission.unregisterListener(this)
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
        displaySpinnerValues(carDataRepository.getCurrentCars())
        requestPermission = RequestPermissionUseCase(activity)
        csvFileManager = CsvFileManager()
        add_car.setOnClickListener {
            carDataRepository.addNewCar(Car("Volkswagen", "Golf MK4", "2003", "Diesel"))
        }

        add_log_btn.setOnClickListener {
            requestWritePermissions()
        }
    }

    private fun requestWritePermissions() {
        val permissionsArray = ArrayList<String>()
        permissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermission.requestPermissions(permissionsArray)
    }

    override fun onNewCarAdded(carList: ArrayList<Car>) {
        displaySpinnerValues(carList)
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
        Toast.makeText(context, "Permission granted. Writing...", Toast.LENGTH_SHORT).show()
        csvFileManager.addFuelLog(FuelLog(spinner1.selectedItem.toString(), fuel_amount_et.text(), fuel_price_et.text(), "01-01-1970"))
    }

    override fun permissionNotGranted() {
        Toast.makeText(context, "Permission not granted. Try again...", Toast.LENGTH_SHORT).show()
    }
}
