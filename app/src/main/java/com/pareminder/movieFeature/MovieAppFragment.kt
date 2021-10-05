package com.pareminder.movieFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pareminder.R
import com.pareminder.common.ScreenState
import com.pareminder.common.State
import com.pareminder.common.printMessage
import com.pareminder.data.network.NetworkProvider
import com.pareminder.data.network.response_models.Movie
import com.pareminder.repository.MovieRepository
import com.pareminder.databinding.FragmentMovieAppBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MovieAppFragment : Fragment(), ScreenState {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding: FragmentMovieAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_app, container, false)
        binding.rvMovieList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val apiService = NetworkProvider.newInstance(requireContext())
        val movieRepository = MovieRepository(apiService)
        val moviewFactory = MovieViewModelFactory(movieRepository)
        movieViewModel = ViewModelProvider(this, moviewFactory).get(MovieViewModel::class.java)
        movieViewModel.screenState = this

        movieViewModel.movies.observe(viewLifecycleOwner, ({
            it?.let {
                setMoviewList(it)
            }
        }))



        return binding.root
    }

    private fun setMoviewList(it: List<Movie>) {
        val moviewAdapter = MoviewAdapter(context, it)
        binding.rvMovieList.adapter = moviewAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieAppFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun lading() {
        runBlocking {
            binding.progressMovie.isVisible = true
        }
    }

    override fun completed() {
        runBlocking {
            binding.progressMovie.isVisible = false
        }

    }

    override fun error(errorCode: State, message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (binding != null)
                binding.progressMovie.isVisible = false

            when (errorCode) {
                State.NO_CONNECTIVITI -> {
//                context?.printMessage(message)
                }
            }
        }

    }
}