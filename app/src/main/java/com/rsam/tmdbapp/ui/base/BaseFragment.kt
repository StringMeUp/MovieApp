package com.rsam.tmdbapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rsam.tmdbapp.BR

abstract class BaseFragment<
        ViewModel : BaseViewModel,
        DataBinding : ViewDataBinding> : Fragment() {
    open var useSharedViewModel: Boolean = false

    protected lateinit var viewModel: ViewModel

    protected lateinit var binding: DataBinding

    @NonNull
    protected abstract fun getViewModelClass(): Class<ViewModel>

    @NonNull
    protected abstract fun getDataBinding(): Class<DataBinding>

    @NonNull
    protected abstract fun getContentView(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(inflater, container)
        setLifecycle(binding, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}
    open fun observeData() {}

    private fun init(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(
            inflater,
            getContentView(),
            container,
            false
        )
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity()).get(getViewModelClass())
        } else {
            ViewModelProvider(this).get(getViewModelClass())
        }
    }

    private fun setLifecycle(
        binding: DataBinding,
        viewModel: ViewModel
    ) {
        binding.run {
            setVariable(BR.viewmodel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
    }
}