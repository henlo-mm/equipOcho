package com.appmovil.mediapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmovil.mediapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun registerUser(email: String, password: String, name: String, lastname: String, role: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, password, name, lastname, role) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, password: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, password) { response ->
            isLogin(response)
        }
    }

}