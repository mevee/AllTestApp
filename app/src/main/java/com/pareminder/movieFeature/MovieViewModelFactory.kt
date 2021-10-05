package com.pareminder.movieFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pareminder.repository.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}