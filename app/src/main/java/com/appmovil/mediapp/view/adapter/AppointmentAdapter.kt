package com.appmovil.mediapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.mediapp.databinding.ManagmentCardsBinding
import com.appmovil.mediapp.model.MedicalAppointment
import com.appmovil.mediapp.view.viewholder.AppointmentViewHolder
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel

class AppointmentAdapter(
    private var appointments: List<MedicalAppointment>,
    private val navController: NavController,
    private val userRole: String,
    private val viewModel: MedicalAppointmentViewModel
) :
    RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ManagmentCardsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppointmentViewHolder(binding, navController, userRole, viewModel)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.setAppointment(appointment)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

}
