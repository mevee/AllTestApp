package com.pareminder.data.network

import com.pareminder.common.Constants
import com.pareminder.data.network.response_models.MoviesResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key")api_key:String="${Constants.APIKEY}",
                                 @Query("language")language:String="${Constants.LANG}",
                                 @Query("page")page:String="1",
    ): Response<MoviesResponse>



}