package com.appmovil.mediapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmovil.mediapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun registerUser(email: String, password: String, name: String, lastname: String, document: String, role: String, specialty: String): LiveData<Boolean> {
        val registerResult = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val result = repository.registerUser(email, password, name, lastname, document, role, specialty)
            registerResult.postValue(result)
        }
        return registerResult
    }

    fun loginUser(email: String, password: String): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val result = repository.loginUser(email, password)
            loginResult.postValue(result)
        }
        return loginResult
    }
    fun logOut(){
        repository.logOut()
    }

}