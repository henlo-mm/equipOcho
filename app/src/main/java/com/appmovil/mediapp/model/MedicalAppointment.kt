package com.appmovil.mediapp.model

import java.io.Serializable

data class MedicalAppointment(
    val id: String = "",
    val date: String = "",
    val time: String = "",
    val doctorName: String = "",
    val doctorSpecialty: String = ""
): Serializable
