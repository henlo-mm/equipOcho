package com.appmovil.mediapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String, name: String, lastname: String, isRegisterComplete: (Boolean) -> Unit) {
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
                                "email" to email
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
