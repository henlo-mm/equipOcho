package com.appmovil.mediapp.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.ManagmentCardsBinding
import com.appmovil.mediapp.model.MedicalAppointment
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel
import com.bumptech.glide.Glide

class AppointmentViewHolder(
    private val binding: ManagmentCardsBinding,
    private val navController: NavController,
    private val userRole: String,
    private val viewModel: MedicalAppointmentViewModel,

) : RecyclerView.ViewHolder(binding.root) {


    fun setAppointment(appointment: MedicalAppointment) {
        binding.appointmentDate.text = appointment.date
        binding.appointmentTime.text = appointment.time
        binding.doctorName.text = if (userRole == "doctor") "Paciente: ${appointment.doctorName}" else appointment.doctorName
        binding.doctorSpecialty.text = appointment.doctorSpecialty

        val translatedSpecialty = translateSpecialty(appointment.doctorSpecialty)
        fetchAndSetDoctorImage(translatedSpecialty)


        binding.itemCardView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("appointment", appointment)

            if (userRole == "doctor") {
                Log.d("Message", "En construcción")
            } else {
                navController.navigate(R.id.action_homeFragment_to_detailAppointmentFragment, bundle)
            }
        }
    }

    fun translateSpecialty(specialty: String): String {
        return when (specialty) {
            "Cardiología" -> "Cardiology"
            "Neurología" -> "Neurology"
            "Odontología" -> "Dentistry"
            else -> specialty
        }
    }

    private fun fetchAndSetDoctorImage(specialty: String) {
        viewModel.fetchDoctorImage(specialty) { imageUrl ->
            Glide.with(binding.root.context)
                .load(imageUrl ?: R.drawable.ic_doctor)
                .into(binding.doctorImage)
        }
    }



}