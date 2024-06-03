package com.appmovil.mediapp.viewmodel

import androidx.lifecycle.ViewModel
import com.appmovil.mediapp.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class MedicalAppointmentViewModel @Inject constructor(
    private val appointmentRepository : AppointmentRepository
) : ViewModel(){

    fun createAppointmentWithAutoAssign(patientId: String, date: String, specialty: String, onComplete: (Boolean) -> Unit) {
        appointmentRepository.assignDoctorAutomatically(specialty) { doctorId ->
            if (doctorId != null) {
                appointmentRepository.createAppointment(patientId, doctorId, date, specialty) { success ->
                    onComplete(success)
                }
            } else {
                onComplete(false)
            }
        }
    }

}