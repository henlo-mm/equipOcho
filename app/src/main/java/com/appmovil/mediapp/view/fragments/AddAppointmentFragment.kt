package com.appmovil.mediapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.FragmentAddAppointmentBinding
import com.appmovil.mediapp.databinding.FragmentEditAppointmentBinding
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AddAppointmentFragment : Fragment() {

    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()
    private lateinit var binding: FragmentAddAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAppointmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeUserData()
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setup() {
        val serviceId = arguments?.getInt("serviceId") ?: 0
        val specialtyName = getSpecialtyName(serviceId)

        binding.addEspecialidad.setText(specialtyName)
        binding.addEspecialidad.isEnabled = false

        binding.addAppointmentButton.setOnClickListener {
            createAppointmentWithAutoAssign()
        }
        appointmentViewModel.fetchUserData()


    }
    private fun observeUserData() {
        appointmentViewModel.userData.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                binding.addName.setText(it["name"] ?: "")
                binding.addLastName.setText(it["lastname"] ?: "")
                binding.addCedula.setText(it["document"] ?: "")

            }
        }

    }

    fun createAppointmentWithAutoAssign() {
        try {

            val specialty = binding.addEspecialidad.text.toString()
            val date = binding.addDate.text.toString()
            val hour = binding.addHour.text.toString()

            appointmentViewModel.createAppointmentWithAutoAssign(
                 date, hour, specialty
            ) { success ->
                try {
                    if (success) {
                        Toast.makeText(context, "Appointment created successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_addAppointmentFragment_to_homeFragment)

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

    private fun getSpecialtyName(serviceId: Int): String {
        return when (serviceId) {
            1 -> "Cardiología"
            2 -> "Neurología"
            3 -> "Odontología"
            else -> "Desconocido"
        }
    }
}