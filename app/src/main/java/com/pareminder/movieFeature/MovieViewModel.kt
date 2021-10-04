package com.pareminder.movieFeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.pareminder.data.network.response_models.Movie
import com.pareminder.data.network.response_models.MoviesResponse
import com.pareminder.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(val repository: MovieRepository) : ViewModel() {
    private val TAG = "MovieViewModel"
    private val _movieList = MutableLiveData<List<Movie>>()

    val movies: LiveData<List<Movie>> get() = _movieList

    init {
        loadAllMovies()
    }

    private fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val movieResponse = repository.getAllPopularMovies()
                Log.d(TAG, "ERR--" + movieResponse.toString())
                val body = movieResponse.body()

                if (body != null) {
                    withContext(Dispatchers.Main) {
                        _movieList.postValue(body.results)
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "ERR--" + e.toString())

            }


        }
    }

}