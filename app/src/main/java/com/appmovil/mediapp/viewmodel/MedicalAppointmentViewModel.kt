package com.appmovil.mediapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmovil.mediapp.model.MedicalAppointment
import com.appmovil.mediapp.repository.AppointmentRepository
import com.appmovil.mediapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalAppointmentViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _appointments = MutableLiveData<List<MedicalAppointment>>()
    val appointments: LiveData<List<MedicalAppointment>> get() = _appointments

    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

    fun createAppointmentWithAutoAssign(date: String, time: String, specialty: String, onComplete: (Boolean) -> Unit) {
        val patientId = authRepository.getCurrentUserUid().toString()
        appointmentRepository.assignDoctorAutomatically(specialty) { doctorId ->
            if (doctorId != null) {
                appointmentRepository.createAppointment(patientId, doctorId, date, time, specialty) { success ->
                    onComplete(success)
                }
            } else {
                onComplete(false)
            }
        }
    }

    fun getPatientAppointments() {
        viewModelScope.launch {
            val patientId = authRepository.getCurrentUserUid()?.toString() ?: return@launch
            appointmentRepository.getPatientAppointments(patientId) { appointments ->
                val updatedAppointments = mutableListOf<MedicalAppointment>()
                var processedCount = 0

                if (appointments.isEmpty()) {
                    _appointments.postValue(updatedAppointments)
                    return@getPatientAppointments
                }

                appointments.forEach { appointment ->
                    val doctorId = appointment["doctorId"] as? String

                    if (doctorId != null) {
                        viewModelScope.launch {

                            appointmentRepository.getDoctorById(doctorId) { doctor ->

                                val doctorName = doctor?.get("name") as? String ?: "Unknown"
                                val id = appointment["id"].toString()
                                val date = appointment["date"].toString()
                                val specialty = appointment["specialty"].toString()

                                updatedAppointments.add(
                                    MedicalAppointment(
                                        id = id,
                                        date = date,
                                        time = date,
                                        doctorName = doctorName,
                                        doctorSpecialty = specialty
                                    )
                                )

                                processedCount++

                                if (processedCount == appointments.size) {
                                    Log.d("ViewModel", "Appointments updated: $updatedAppointments")
                                    _appointments.postValue(updatedAppointments)
                                }
                            }
                        }
                    } else {
                        processedCount++
                        if (processedCount == appointments.size) {
                            _appointments.postValue(updatedAppointments)
                        }
                    }
                }
            }
        }
    }


    fun editAppointmentByPatient(appointmentId: String, newDate: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            appointmentRepository.editAppointmentByPatient(appointmentId, newDate) {  success ->
                onComplete(success)
            }
        }

    }

    fun fetchUserData() {
        val userId = authRepository.getCurrentUserUid().toString()
        viewModelScope.launch {
            val result = authRepository.getUserData(userId)
            val stringResult = result?.mapValues { it.value.toString() } as Map<String, String>
            _userData.postValue(stringResult)
        }
    }

}
