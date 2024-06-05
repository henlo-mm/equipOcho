package com.appmovil.mediapp.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL_CALENDAR = "https://www.googleapis.com/calendar/v3/"

    private val retrofitCalendar by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CALENDAR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val calendarApi: GoogleCalendarApi by lazy {
        retrofitCalendar.create(GoogleCalendarApi::class.java)
    }

}
