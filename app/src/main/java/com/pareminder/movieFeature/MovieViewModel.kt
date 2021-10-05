package com.pareminder.movieFeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pareminder.common.ScreenState
import com.pareminder.common.State
import com.pareminder.data.network.ApiException
import com.pareminder.data.network.interceptors.NoConnectivityException
import com.pareminder.data.network.response_models.Movie
import com.pareminder.data.network.response_models.MoviesResponse
import com.pareminder.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(val repository: MovieRepository) : ViewModel() {
    private val TAG = "MovieViewModel"
    private val _movieList = MutableLiveData<List<Movie>>()
    lateinit var screenState: ScreenState

    val movies: LiveData<List<Movie>> get() = _movieList

    init {
        loadAllMovies()
    }

    private fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            screenState?.lading()
            try {
                val movieResponse: MoviesResponse = repository.getAllPopularMovies()
                if (movieResponse != null) {
                    withContext(Dispatchers.Main) {
                        _movieList.postValue(movieResponse.results)
                        screenState.completed()
                    }
                }
            } catch (e: ApiException) {
                screenState.error(message = e.toString())
            } catch (e: NoConnectivityException) {
                screenState.error(errorCode = State.NO_CONNECTIVITI, message = e.toString())
            } catch (e: Exception) {
                screenState.error(message = e.toString())
            }
        }
    }

}