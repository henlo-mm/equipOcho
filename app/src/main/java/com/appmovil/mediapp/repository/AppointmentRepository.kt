package com.appmovil.mediapp.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val context: Context
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
               // sendEmailReminder(patientId, date)
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_ERROR", "Error creating appointment", e)
                onComplete(false)
            }
    }
   /* private fun sendEmailReminder(patientId: String, date: String) {
        firestore.collection("users").document(patientId).get()
            .addOnSuccessListener { document ->
                val email = document.getString("email")
                if (email != null) {
                    val credential = GoogleAuth.getCredentials(context)
                    val gmailService = Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential)
                        .setApplicationName("MediApp")
                        .build()

                    val subject = "Recordatorio de Cita Médica"
                    val bodyText = "Tiene una cita médica programada para la fecha: $date"

                    GmailApi.sendEmail(gmailService, email, "esperanzacalderon@gmail.com", subject, bodyText)
                }
            }
    }

    */
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

    fun getPatientAppointments(patientId: String, onComplete: (List<Map<String, Any>>) -> Unit) {
        firestore.collection("appointments")
            .whereEqualTo("patientId", patientId)
            .get()
            .addOnSuccessListener { documents ->
                val appointments = documents.map { it.data }
                onComplete(appointments)
            }
            .addOnFailureListener {
                onComplete(emptyList())
            }
    }

    fun editAppointmentByPatient(appointmentId: String, newDate: String, onComplete: (Boolean) -> Unit) {
        firestore.collection("appointments").document(appointmentId)
            .update("date", newDate)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun editAppointmentByDoctor(appointmentId: String, newStatus: String, onComplete: (Boolean) -> Unit) {
        firestore.collection("appointments").document(appointmentId)
            .update("status", newStatus)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun deleteAppointment(appointmentId: String, onComplete: (Boolean) -> Unit) {
        firestore.collection("appointments").document(appointmentId)
            .delete()
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
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