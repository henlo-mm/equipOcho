package com.appmovil.mediapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun registerUser(
        email: String, password: String, name: String, lastname: String,
        document: String, role: String, specialty: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty()) {
                try {
                    val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    val user = authResult.user
                    if (user != null) {
                        val userId = user.uid
                        val userMap = hashMapOf(
                            "name" to name,
                            "lastname" to lastname,
                            "document" to document,
                            "email" to email,
                            "role" to role,
                            "specialty" to specialty
                        )
                        firestore.collection("users").document(userId).set(userMap).await()
                        return@withContext true
                    } else {
                        return@withContext false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@withContext false
                }
            } else {
                return@withContext false
            }
        }
    }


    suspend fun loginUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val task = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                task.user != null
            } catch (e: Exception) {
                Log.d("Exception grg", e.toString())
                e.printStackTrace()
                false
            }
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }

    suspend fun getUserData(): Map<String, Any>? {
        val userUid = getCurrentUserUid().toString()
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = firestore.collection("users").document(userUid).get().await()
                if (documentSnapshot.exists()) {
                    documentSnapshot.data
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

}
