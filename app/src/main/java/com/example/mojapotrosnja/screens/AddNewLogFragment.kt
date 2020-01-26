package com.example.mojapotrosnja.screens


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.mojapotrosnja.R
import kotlinx.android.synthetic.main.fragment_add_new_log.*

/**
 * A simple [Fragment] subclass.
 */
class AddNewLogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arraySpinner = arrayOf("1", "2", "3", "4", "5", "6", "7")
        context?.also {
            val adapter = ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item, arraySpinner
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }
    }

}
