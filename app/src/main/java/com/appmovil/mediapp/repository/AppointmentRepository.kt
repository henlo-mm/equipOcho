package com.appmovil.mediapp.repository

import com.google.firebase.firestore.FirebaseFirestore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun createAppointment(patientId: String, doctorId: String, date: String, specialty: String, onComplete: (Boolean) -> Unit) {
        val appointmentMap = hashMapOf(
            "patientId" to patientId,
            "doctorId" to doctorId,
            "date" to date,
            "specialty" to specialty,
            "status" to "pending"
        )
        firestore.collection("appointments").add(appointmentMap)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun getDoctorAppointments(doctorId: String, onComplete: (List<Map<String, Any>>) -> Unit) {
        firestore.collection("appointments")
            .whereEqualTo("doctorId", doctorId)
            .get()
            .addOnSuccessListener { documents ->
                val appointments = documents.map { it.data }
                onComplete(appointments)
            }
            .addOnFailureListener {
                onComplete(emptyList())
            }
    }

    fun assignDoctorAutomatically(specialty: String, onComplete: (String?) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("role", "doctor")
            .whereEqualTo("specialty", specialty)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onComplete(null)
                } else {
                    val doctorList = documents.documents
                    val randomDoctor = doctorList.random()
                    onComplete(randomDoctor.id)
                }
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }
}