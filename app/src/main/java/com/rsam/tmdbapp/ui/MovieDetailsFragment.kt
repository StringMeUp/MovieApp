package com.rsam.tmdbapp.ui

import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.databinding.FragmentMovieDetailsBinding
import com.rsam.tmdbapp.ui.base.BaseFragment
import com.rsam.tmdbapp.util.Alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<SharedViewModel, FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {
    override var useSharedViewModel: Boolean = true

    override fun provideViewmodel(): Class<SharedViewModel> = SharedViewModel::class.java
    override fun inflateBinding(): Class<FragmentMovieDetailsBinding>
    { return FragmentMovieDetailsBinding::class.java }

    override fun setContent(): Int = R.layout.fragment_movie_details

    override fun setUpView() {
        super.setUpView()
    }

    override fun setUpViewBinding() {
        super.setUpViewBinding()
    }

    override fun setUpViewModelBinding() {
        super.setUpViewModelBinding()
        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Alert.displayErrorDialog(it, requireContext())
            }
        })
    }
}