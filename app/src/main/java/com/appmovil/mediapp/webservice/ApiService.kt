package com.appmovil.mediapp.webservice

import com.appmovil.mediapp.data.PexelsResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: NJZj3O42q0EaIgrYzI0Bv7VcLMaGX4KGPSIiRkM4an4b4tScJrlNa4P3")
    @GET("v1/search")
    fun searchImages(@Query("query") query: String, @Query("per_page") perPage: Int = 10): Call<PexelsResponse>
}