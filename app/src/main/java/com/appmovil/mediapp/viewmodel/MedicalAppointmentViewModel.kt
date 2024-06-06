package com.appmovil.mediapp.viewmodel

import android.util.Log
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
                    if (!success) {
                        Log.e("APPOINTMENT_ERROR", "Failed to create appointment for patient: $patientId, doctor: $doctorId, date: $date, specialty: $specialty")
                    }
                    onComplete(success)
                }
            } else {
                Log.e("APPOINTMENT_ERROR", "No doctor found for specialty: $specialty")
                onComplete(false)
            }
        }
    }

}