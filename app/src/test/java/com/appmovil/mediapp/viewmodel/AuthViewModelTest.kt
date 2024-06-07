package com.appmovil.mediapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmovil.mediapp.repository.AuthRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith

class AuthViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var AuthViewModel: AuthViewModel
    private lateinit var mockAuthRepository: AuthRepository


    @Before
    fun setUp() {
        mockAuthRepository = Mockito.mock(AuthRepository::class.java)
        AuthViewModel = AuthViewModel(mockAuthRepository)
    }

    @Test
    fun `test de login` (){
        //given
        // Given
        val email = "robert@gmail.com"
        val password = "hola"
        val isLoginSuccessful = true

        `when`(mockAuthRepository.loginUser(email, password, any())).thenAnswer {
            val callback = it.arguments[2] as (Boolean) -> Unit
            callback(isLoginSuccessful)
        }

        // When
        var loginResult: Boolean? = null
        AuthViewModel.loginUser(email, password) { result ->
            loginResult = result
        }

        // Then
        assertTrue(loginResult!!)
        verify { mockAuthRepository.loginUser(email, password, any()) }
    }
}