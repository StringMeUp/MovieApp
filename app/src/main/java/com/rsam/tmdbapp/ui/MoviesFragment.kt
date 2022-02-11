package com.rsam.tmdbapp.ui

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.adapters.MoviesAdapter
import com.rsam.tmdbapp.adapters.OnButtonClickListener
import com.rsam.tmdbapp.adapters.OnCardClickListener
import com.rsam.tmdbapp.data.Movie
import com.rsam.tmdbapp.databinding.FragmentMoviesBinding
import com.rsam.tmdbapp.ui.base.BaseFragment
import com.rsam.tmdbapp.util.Alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<SharedViewModel, FragmentMoviesBinding>(FragmentMoviesBinding::inflate),
    OnCardClickListener,
    OnButtonClickListener {

    private val newsAdapter = MoviesAdapter(
        this@MoviesFragment,
        this@MoviesFragment
    )

    override var useSharedViewModel: Boolean = true
    override fun provideViewmodel(): Class<SharedViewModel> = SharedViewModel::class.java
    override fun inflateBinding(): Class<FragmentMoviesBinding> = FragmentMoviesBinding::class.java
    override fun setContent(): Int = R.layout.fragment_movies
    override fun onCardClicked(movieId: Int) {
        viewModel.getDetailsInfo(movieId)
    }

    override fun onButtonClicked(movie: Movie) {
        viewModel.saveToFavorites(movie)
        Toast.makeText(
            requireContext(),
            getString(R.string.saved_movie_info_toast),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun setUpView() {
        super.setUpView()
        viewModel.fetchMovieData()
        binding.testRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    override fun setUpViewModelBinding() {
        super.setUpViewModelBinding()
        viewModel.moviesList.observe(viewLifecycleOwner, { article ->
            article?.let {
                newsAdapter.moviesList = it
            }
        })

        viewModel.shouldNavigate.observe(viewLifecycleOwner, { shouldNavigate ->
            shouldNavigate?.let {
                if (it)
                    findNavController().navigate(R.id.action_moviesFragment_to_movieDetailsFragment)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Alert.displayErrorDialog(it, requireContext())
            }
        })
    }
}