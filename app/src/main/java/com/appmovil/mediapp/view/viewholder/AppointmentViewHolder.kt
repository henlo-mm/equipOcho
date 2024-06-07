package com.appmovil.mediapp.view.viewholders

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.ManagmentCardsBinding
import com.appmovil.mediapp.model.MedicalAppointment

class AppointmentViewHolder(private val binding: ManagmentCardsBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingAppointment = binding
    val navController = navController

    fun setAppointment(appointment: MedicalAppointment) {

        bindingAppointment.appointmentDate.text = appointment.date
        bindingAppointment.appointmentTime.text = appointment.time
        bindingAppointment.doctorName.text = appointment.doctorName
        bindingAppointment.doctorSpecialty.text = appointment.doctorSpecialty

        bindingAppointment.itemCardView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("appointment", appointment)
            navController.navigate(
                R.id.action_homeFragment_to_detailAppointmentFragment, bundle
            )
        }

    }
}
