package com.appmovil.mediapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmovil.mediapp.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun registerUser(email: String, password: String, name: String, lastname: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, password, name, lastname) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, password: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, password) { response ->
            isLogin(response)
        }
    }

}