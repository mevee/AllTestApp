package com.pareminder.movieFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pareminder.R
import com.pareminder.data.network.NetworkProvider
import com.pareminder.data.repository.MovieRepository

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieAppFragment : Fragment() {
     private var param1: String? = null
    private var param2: String? = null
    private lateinit var movieViewModel: MovieViewModel

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
        val apiService = NetworkProvider.newInstance()
        val movieRepository = MovieRepository(apiService)
        val moviewFactory = MovieViewModelFactory(movieRepository)
        movieViewModel = ViewModelProvider(this,moviewFactory).get(MovieViewModel::class.java)

        movieViewModel.movies.observe(viewLifecycleOwner,({
            it?.let {

            }
        }))
        return inflater.inflate(R.layout.fragment_movie_app, container, false)
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
}