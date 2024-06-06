package com.appmovil.mediapp.repository

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
    suspend fun registerUser(email: String, password: String, name: String, lastname: String, role: String, isRegisterComplete: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            user?.let {
                                val userId = it.uid
                                val userMap = hashMapOf(
                                    "name" to name,
                                    "lastname" to lastname,
                                    "email" to email,
                                    "role" to role
                                )
                                firestore.collection("users").document(userId).set(userMap)
                                    .addOnSuccessListener {
                                        isRegisterComplete(true)
                                    }
                                    .addOnFailureListener {
                                        isRegisterComplete(false)
                                    }
                            } ?: run {
                                isRegisterComplete(false)
                            }
                        } else {
                            isRegisterComplete(false)
                        }
                    }
                    .addOnFailureListener {
                        it.printStackTrace()
                        isRegisterComplete(false)
                    }
            } else {
                isRegisterComplete(false)
            }
        }

    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val task = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                task.user != null
            } catch (e: Exception) {
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

    suspend fun getUserData(uid: String): Map<String, Any>? {
        return withContext(Dispatchers.IO) {
            try {
                val document = firestore.collection("users").document(uid).get().await()
                if (document.exists()) {
                    document.data
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
