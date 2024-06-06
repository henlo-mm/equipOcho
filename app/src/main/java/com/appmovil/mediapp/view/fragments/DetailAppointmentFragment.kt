package com.appmovil.mediapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
      /*  binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


       */
    }

    private fun controladores() {

        /*
        binding.deleteButton.setOnClickListener {
            deleteAppointment()
        }

         */

    /*    binding.editButton.setOnClickListener {
            val bundle = Bundle()
            if (receivedAppointment != null) {
                bundle.putSerializable("appointmentData", receivedAppointment)
                Log.d("EditButton", "Navigating to edit with data: $receivedAppointment")
                findNavController().navigate(R.id.action_detailAppointmentFragment_to_editAppointmentFragment, bundle)
            } else {
                Log.e("EditButton", "Received appointment is null!")
            }
        }
     */

    }

    private fun getAppointmentData() {
        val receivedBundle = arguments
        receivedAppointment = receivedBundle?.getSerializable("appointment") as MedicalAppointment

   /*     binding.numberAppointment.text = "#${receivedAppointment.id}"
        binding.titleTextDetailsName.text = "${receivedAppointment.dogName}"
        binding.petBreedName.text = "${receivedAppointment.breed}"
        binding.petSymptoms.text = "${receivedAppointment.symptom}"
        binding.ownerName.text = "Propietario: ${receivedAppointment.ownerName}"
        binding.ownerPhone.text = "Teléfono: ${receivedAppointment.phone}"

    */


    }


}