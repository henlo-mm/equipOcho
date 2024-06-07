package com.appmovil.mediapp.repository

import android.content.Context
import android.util.Log
import com.appmovil.mediapp.data.PexelsResponse
import com.appmovil.mediapp.webservice.ApiClient
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val context: Context
) {

    fun createAppointment(patientId: String, doctorId: String, date: String, time: String, specialty: String, onComplete: (Boolean) -> Unit) {
        val appointmentId = firestore.collection("appointments").document().id

        val appointmentMap = hashMapOf(
            "id" to appointmentId,
            "patientId" to patientId,
            "doctorId" to doctorId,
            "time" to time,
            "date" to date,
            "specialty" to specialty,
            "status" to "pending"
        )
        Log.d("CREATE_APPOINTMENT", "Datos de la cita: $appointmentMap")

        firestore.collection("appointments").document(appointmentId).set(appointmentMap)
            .addOnSuccessListener {
               // sendEmailReminder(patientId, date)
                Log.d("CREATE_APPOINTMENT", "Cita creada exitosamente")

                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_ERROR", "Error creating appointment", e)
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

    suspend fun getPatientAppointments(patientId: String, onComplete: (List<Map<String, Any>>) -> Unit) {
        return withContext(Dispatchers.IO) {
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

    }

    suspend fun getDoctorById(doctorId: String, onComplete: (Map<String, Any>?) -> Unit) {
        return withContext(Dispatchers.IO) {
            firestore.collection("users").document(doctorId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        onComplete(document.data)
                    } else {
                        onComplete(null)
                    }
                }
                .addOnFailureListener {
                    onComplete(null)
                }
        }

    }

    suspend fun getPatientById(patientId: String, onComplete: (Map<String, Any>?) -> Unit) {
        return withContext(Dispatchers.IO) {
            firestore.collection("users").document(patientId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        onComplete(document.data)
                    } else {
                        onComplete(null)
                    }
                }
                .addOnFailureListener {
                    onComplete(null)
                }
        }

    }

    suspend fun editAppointmentByPatient(appointmentId: String, newDate: String, time: String, specialty: String, onComplete: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            val updates = hashMapOf<String, Any>(
                "date" to newDate,
                "time" to time,
                "specialty" to specialty
            )

            firestore.collection("appointments").document(appointmentId)
                .update(updates)
                .addOnSuccessListener {
                    Log.d("FirestoreUpdate", "DocumentSnapshot successfully updated!")

                    onComplete(true)
                }
                .addOnFailureListener {e->
                    Log.w("FirestoreUpdate", "Error updating document", e)

                    onComplete(false)
                }
        }

    }


    suspend fun deleteAppointment(appointmentId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                firestore.collection("appointments").document(appointmentId)
                    .delete()
                    .await()

                return@withContext true

            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }

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

    suspend fun getDoctorImage(specialty: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.pexelsApiService.searchImages(specialty, 1).execute()
                if (response.isSuccessful) {
                    val photos = response.body()?.photos
                    if (!photos.isNullOrEmpty()) {
                        return@withContext photos[0].src.medium
                    }
                }
                null
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching image", e)
                null
            }
        }
    }


}