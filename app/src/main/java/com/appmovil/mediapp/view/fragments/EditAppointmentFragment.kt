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
    }

    private fun getAppointmentData() {
        val receivedBundle = arguments

        receivedAppointment = receivedBundle?.getSerializable("appointmentData") as MedicalAppointment


    }

    private fun validateData() {

       /* val listEditText = listOf(binding.nameEditText, binding.nameOwnerEditText, binding.razaAutoCompleteTextView, binding.telephoneEditText)

        fun isFormFilled(): Boolean {
            return listEditText.all { it.text.isNotEmpty() }
        }

        listEditText.forEach { editText ->
            editText.addTextChangedListener {
                binding.btnEdit.isEnabled = isFormFilled()
                binding.btnEdit.setTextColor(if (binding.btnEdit.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))
            }
        }

        binding.btnEdit.isEnabled = isFormFilled()
        binding.btnEdit.setTextColor(if (binding.btnEdit.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))

        binding.btnEdit.setOnClickListener {
            updateAppointment()
        }

        */
    }

    private fun updateAppointment(){

        val id = receivedAppointment.id

        /*
        val dogName = binding.nameEditText.text.toString()

        val appointment = MedicalAppointment(id = id, )
        appointmentViewModel.editAppointmentByPatient(id, date) {success ->

            if (success) {
                Toast.makeText(context, "Appointment created successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addAppointmentFragment_to_homeFragment)

            } else {
                Toast.makeText(context, "Failed to create appointment", Toast.LENGTH_SHORT).show()
            }

        }
        Toast.makeText(context,"¡Su cita se ha actualizado correctamente!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)

         */

    }

}