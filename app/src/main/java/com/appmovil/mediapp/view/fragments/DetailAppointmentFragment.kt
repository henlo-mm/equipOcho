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
import com.appmovil.mediapp.databinding.FragmentDetailAppointmentBinding
import com.appmovil.mediapp.model.MedicalAppointment
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentDetailAppointmentBinding
    private lateinit var receivedAppointment: MedicalAppointment
    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailAppointmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppointmentData()
        controladores()
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        appointmentViewModel.fetchUserData()

    }
    private fun controladores() {

        binding.deleteBtn.setOnClickListener {
            deleteAppointment()
        }

        binding.editBtn.setOnClickListener {
            val bundle = Bundle()
            if (receivedAppointment != null) {
                bundle.putSerializable("appointmentData", receivedAppointment)
                findNavController().navigate(R.id.action_detailAppointmentFragment_to_editAppointmentFragment, bundle)
            } else {
                Log.e("EditButton", "Received appointment is null!")
            }
        }

    }

    private fun getAppointmentData() {
        val receivedBundle = arguments

        appointmentViewModel.userData.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                val fullName = "${it["lastname"] ?: ""} ${it["name"] ?: ""}"
                binding.userName.text = fullName
                binding.document.text =  (it["document"] ?: "")

            }
        }
        receivedAppointment = receivedBundle?.getSerializable("appointment") as MedicalAppointment

        binding.doctorName.text = "${receivedAppointment.doctorName}"
        binding.specialty.text = "${receivedAppointment.doctorSpecialty}"
        binding.date.text = "${receivedAppointment.date}"
        binding.time.text = "${receivedAppointment.time}"

    }

    private fun deleteAppointment() {
        val receivedBundle = arguments

        receivedAppointment = receivedBundle?.getSerializable("appointment") as MedicalAppointment

        appointmentViewModel.deleteAppointment(
            receivedAppointment.id
        ) { success ->
            if (success) {
                Toast.makeText(context, "Appointment deleted successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                appointmentViewModel.getPatientAppointments()

            } else {
                Toast.makeText(context, "Failed to delete appointment", Toast.LENGTH_SHORT).show()
            }

        }
    }



}