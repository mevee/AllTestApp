package com.pareminder.data.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/discover/movie")
    suspend fun getAllMovies(@Field("api_key") api_key:String,): List<String>

}