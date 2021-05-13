package com.rsam.tmdbapp.ui

import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.databinding.FragmentMovieDetailsBinding
import com.rsam.tmdbapp.ui.base.BaseFragment
import com.rsam.tmdbapp.util.Alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<SharedViewModel, FragmentMovieDetailsBinding>() {
    override var useSharedViewModel: Boolean = true
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java
    override fun getDataBinding(): Class<FragmentMovieDetailsBinding> {
        return FragmentMovieDetailsBinding::class.java
    }

    override fun getContentView(): Int = R.layout.fragment_movie_details

    override fun observeData() {
        super.observeData()
        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Alert.displayErrorDialog(it, requireContext())
            }
        })
    }
}