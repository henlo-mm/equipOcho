package com.appmovil.mediapp.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.googleapis.com/calendar/v3/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GoogleCalendarApi by lazy {
        retrofit.create(GoogleCalendarApi::class.java)
    }
}

interface GoogleCalendarApi {
}