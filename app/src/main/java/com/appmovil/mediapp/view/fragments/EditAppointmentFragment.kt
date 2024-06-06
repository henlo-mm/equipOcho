package com.appmovil.mediapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.FragmentEditAppointmentBinding
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EditAppointmentFragment : Fragment() {
    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()
    private lateinit var binding: FragmentEditAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAppointmentBinding.inflate(inflater)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    private fun setup() {
        binding.button2.setOnClickListener {
            createAppointmentWithAutoAssign()
        }

    }

    fun createAppointmentWithAutoAssign() {
        try {
            appointmentViewModel.createAppointmentWithAutoAssign(
                "2020-05-10",
                "Cardiology"
            ) { success ->
                try {
                    if (success) {
                        Toast.makeText(context, "Appointment created successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to create appointment", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("NAVIGATION", "Navigation failed inside lambda", e)
                }
            }
        } catch (e: Exception) {
            Log.e("NAVIGATION", "Navigation failed", e)
        }
    }

}