package com.appmovil.mediapp.webservice
import com.google.api.services.calendar.model.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleCalendarApi {
    @POST("calendars/primary/events")
    fun createEvent(@Body event: Event): Call<Event>
}