package com.appmovil.mediapp.viewmodel

import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appmovil.mediapp.repository.AppointmentRepository
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


    @Before
    fun setUp() {
        mockAppointmentRepository = Mockito.mock(AppointmentRepository::class.java)

    }

    @Test
    fun `test de datos de usuario` () = runBlockingTest{


    }



}

