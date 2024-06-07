package com.appmovil.mediapp.viewmodel

import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appmovil.mediapp.repository.AppointmentRepository
import com.appmovil.mediapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
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
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith

class MedicalAppointmentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var MedicalAppointmentViewModel: MedicalAppointmentViewModel
    private lateinit var mockAppointmentRepository: AppointmentRepository
    private lateinit var mockAuthRepository: AuthRepository


    @Before
    fun setUp() {
        mockAppointmentRepository = Mockito.mock(AppointmentRepository::class.java)
        mockAuthRepository = Mockito.mock(AuthRepository::class.java)
        MedicalAppointmentViewModel = MedicalAppointmentViewModel(mockAppointmentRepository,mockAuthRepository)
    }

    @Test
    fun test_delete_appointment () = runBlockingTest{
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val id = "v5SDQ40TPPyEHM6uqsy9"
        val isDelete = false

        `when`(mockAppointmentRepository.deleteAppointment(id)).thenReturn(isDelete)

        val liveData = MedicalAppointmentViewModel.deleteAppointment(id)

        val observer = mock(Observer::class.java) as Observer<Boolean>
        liveData.observeForever(observer)

        // Capture the emitted value
        val captor = argumentCaptor<Boolean>()
        verify(observer).onChanged(captor.capture())

        // Then
        assertEquals(isDelete, captor.firstValue)

    }



}

