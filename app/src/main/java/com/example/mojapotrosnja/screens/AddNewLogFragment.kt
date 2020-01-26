package com.example.mojapotrosnja.screens


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.mojapotrosnja.R
import com.example.mojapotrosnja.common.toStringArray
import com.example.mojapotrosnja.models.Car
import com.example.mojapotrosnja.utils.CarDataRepository
import com.example.mojapotrosnja.utils.SharedPreferencesManager
import kotlinx.android.synthetic.main.fragment_add_new_log.*

/**
 * A simple [Fragment] subclass.
 */
class AddNewLogFragment : Fragment(), CarDataRepository.NewCarAddedListener {

    lateinit var carDataRepository: CarDataRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carDataRepository = CarDataRepository(SharedPreferencesManager(context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)!!))
        carDataRepository.registerListener(this)
        add_car.setOnClickListener {
            carDataRepository.addNewCar(Car("Volkswagen", "Golf MK4", "2003", "Diesel"))
        }
    }

    override fun onNewCarAdded(carList: ArrayList<Car>) {
        context?.also {
            val adapter = ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item, carList.toStringArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }
    }

    override fun onStop() {
        super.onStop()
        carDataRepository.unregisterListener(this)
    }
}
