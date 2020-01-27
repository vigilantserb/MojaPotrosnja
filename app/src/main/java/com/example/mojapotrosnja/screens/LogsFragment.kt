package com.example.mojapotrosnja.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mojapotrosnja.R
import com.example.mojapotrosnja.utils.CsvFileManager
import kotlinx.android.synthetic.main.fragment_logs.*

class LogsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val csvFileManager = CsvFileManager()
        val logsAdapter = LogsAdapter(csvFileManager.getFuelLogs())
        fuel_log_list_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = logsAdapter
        }
    }
}
