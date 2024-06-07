package com.appmovil.mediapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appmovil.mediapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*
import org.mockito.kotlin.argumentCaptor

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
    fun `test de login` () = runBlockingTest{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val email = "robert@gmail.com"
        val password = "hola"
        val loginSuccess = true

        // when
        `when`(mockAuthRepository.loginUser(email, password)).thenReturn(loginSuccess)

        val liveData = AuthViewModel.loginUser(email, password)

        // Create an observer to observe the LiveData
        val observer = mock(Observer::class.java) as Observer<Boolean>
        liveData.observeForever(observer)

        // Capture the emitted value
        val captor = argumentCaptor<Boolean>()
        verify(observer).onChanged(captor.capture())

        // Then
        assertEquals(loginSuccess, captor.firstValue)




    }
    @Test
    fun `test de register` () = runBlockingTest{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val email = "robert@gmail.com"
        val password = "hola"
        val name = "robert"
        val lastname = "gil"
        val document = "12345"
        val role = "empleado"
        val specialty = "neurologo"
        val isRegister = true

        // when
        `when`(mockAuthRepository.registerUser(email, password, name, lastname, document, role, specialty, Mockito.any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean) -> Unit>(7)
            callback(isRegister)
        }

        // Create a captor for the callback
        val callbackCaptor = argumentCaptor<(Boolean) -> Unit>()
        AuthViewModel.registerUser(email, password, name, lastname, document, role, specialty) { result ->
            assertEquals(isRegister, result)
        }

        // Capture the callback and invoke it with the expected value
        verify(mockAuthRepository).registerUser(
            eq(email), eq(password), eq(name), eq(lastname), eq(document), eq(role), eq(specialty), callbackCaptor.capture()
        )

        // Simulate repository's callback
        callbackCaptor.firstValue.invoke(isRegister)
    }

}