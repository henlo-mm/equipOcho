package com.appmovil.mediapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appmovil.mediapp.R
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AddAppointmentFragment : Fragment() {

    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // createAppointmentWithAutoAssign()
    }

    fun createAppointmentWithAutoAssign() {
        appointmentViewModel.createAppointmentWithAutoAssign(
            "2020-05-10",
            "Cardiology"
        ) { success ->
            if (success) {
                Toast.makeText(context, "Appointment created successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create appointment", Toast.LENGTH_SHORT).show()

            }
        }
    }
}