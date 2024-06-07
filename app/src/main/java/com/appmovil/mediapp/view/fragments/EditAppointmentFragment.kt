package com.appmovil.mediapp.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.FragmentEditAppointmentBinding
import com.appmovil.mediapp.model.MedicalAppointment
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EditAppointmentFragment : Fragment() {
    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()
    private lateinit var binding: FragmentEditAppointmentBinding
    private lateinit var receivedAppointment: MedicalAppointment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAppointmentBinding.inflate(inflater)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppointmentData()
        validateData()
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAppointmentData() {
        val receivedBundle = arguments

        receivedAppointment = receivedBundle?.getSerializable("appointmentData") as MedicalAppointment

        binding.editTextDate.setText(receivedAppointment.date)
        binding.editTextTime.setText(receivedAppointment.time)

        val specialties = resources.getStringArray(R.array.specialties_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specialties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSpecialty.adapter = adapter

        val position = specialties.indexOf(receivedAppointment.doctorSpecialty)
        if (position >= 0) {
            binding.spinnerSpecialty.setSelection(position)
        }


    }

    private fun validateData() {

        val listEditText = listOf(binding.spinnerSpecialty, binding.editTextDate, binding.editTextTime)


/*
        fun isFormFilled(): Boolean {
            return listEditText.all { it.text.isNotEmpty() }
        }

        listEditText.forEach { editText ->
            binding.button2.isEnabled = isFormFilled()
            binding.button2.setTextColor(if (binding.button2.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))

        }

        binding.button2.isEnabled = isFormFilled()
        binding.button2.setTextColor(if (binding.button2.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))
*/
        binding.button2.setOnClickListener {
            updateAppointment()
        }

    }

    private fun updateAppointment(){

        val id = receivedAppointment.id

        val specialty = binding.spinnerSpecialty.selectedItem.toString()
        val time = binding.editTextTime.text.toString()
        val date = binding.editTextDate.text.toString()

        appointmentViewModel.editAppointmentByPatient(id, date, time, specialty) {success ->

            if (success) {
                Toast.makeText(context,"¡Su cita se ha actualizado correctamente!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)

            } else {
                Toast.makeText(context, "Failed to update appointment", Toast.LENGTH_SHORT).show()
            }

        }



    }

}