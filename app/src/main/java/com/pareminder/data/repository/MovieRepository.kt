package com.pareminder.data.repository

import com.pareminder.data.network.ApiService

class MovieRepository(private val movieApiService: ApiService ) {

    public suspend fun getAllPopularMovies() = movieApiService.getPopularMovies()

}