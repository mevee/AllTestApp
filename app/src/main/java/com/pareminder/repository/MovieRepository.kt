package com.pareminder.repository

import com.pareminder.data.network.ApiService
import com.pareminder.data.network.SafeApiRequest
import com.pareminder.data.network.response_models.MoviesResponse

class MovieRepository(private val movieApiService: ApiService ) :SafeApiRequest(){

    suspend fun getAllPopularMovies() : MoviesResponse = apiRequest { movieApiService.getPopularMovies() }

}