package com.appmovil.mediapp.viewmodel

import androidx.lifecycle.ViewModel
import com.appmovil.mediapp.repository.AppointmentRepository
import com.appmovil.mediapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class MedicalAppointmentViewModel @Inject constructor(
    private val appointmentRepository : AppointmentRepository,
    private val authRepository: AuthRepository

) : ViewModel(){

    fun createAppointmentWithAutoAssign(date: String, specialty: String, onComplete: (Boolean) -> Unit) {
        val patientId = authRepository.getCurrentUserUid().toString()

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