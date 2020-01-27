package com.example.mojapotrosnja.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojapotrosnja.R
import com.example.mojapotrosnja.models.FuelLog
import kotlinx.android.synthetic.main.item_fuel_log.view.*

class LogsAdapter(var users: ArrayList<FuelLog>) :
    RecyclerView.Adapter<LogsAdapter.FuelLogViewHolder>() {

    fun updateLogs(newLogs: List<FuelLog>) {
        users.clear()
        users.addAll(newLogs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = FuelLogViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_fuel_log, parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: FuelLogViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class FuelLogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val carData = view.car_data_tv
        private val fuelAmount = view.fuel_amount_tv
        private val fuelPrice = view.fuel_price_tv
        private val date = view.date_tv
        fun bind(log: FuelLog) {
            carData.text = "Automobil: ${log.carData}"
            fuelAmount.text = "Kolicina: ${log.fuelAmount}"
            fuelPrice.text = "Cena: ${log.fuelPrice}"
            date.text = "Datum: ${log.fuelDate}"
        }
    }
}
