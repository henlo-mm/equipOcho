package com.appmovil.mediapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {


    fun registerUser(email: String, password: String, name: String, lastname: String, role: String, isRegisterComplete: (Boolean) -> Unit) {
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

    fun loginUser(email: String, password: String, isLogin: (Boolean) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}
