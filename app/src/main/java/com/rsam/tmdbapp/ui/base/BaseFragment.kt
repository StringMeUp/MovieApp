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

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<
        ViewModel : BaseViewModel,
        VDBinding : ViewDataBinding>(private val inflate: Inflate<VDBinding>) : Fragment() {

    /**If VM needs to be shared set to true.*/
    open var useSharedViewModel: Boolean = false

    /**Tru using mostly VB as it isn't as heavy as DB.*/
    open var usesViewBinding: Boolean = false

    open fun setUpView() {}
    open fun setUpViewBinding() {}
    open fun setUpViewModelBinding() {}

    private var _viewModel: ViewModel? = null
    protected val viewModel
        get() = _viewModel!!

    private var _binding: VDBinding? = null
    protected val binding
        get() = _binding!!

    private var _viewbinding: VDBinding? = null
    protected val viewBinding
        get() = _viewbinding!!

    @NonNull
    protected abstract fun provideViewmodel(): Class<ViewModel>

    @NonNull
    protected abstract fun inflateBinding(): Class<VDBinding>

    @NonNull
    protected abstract fun setContent(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //databinding
        init(inflater, container)
        setLifecycle(binding, viewModel)

        //viewbinding
        _viewbinding = inflate.invoke(inflater, container, false)

        return if (usesViewBinding) viewBinding.root else binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewBinding()
        setUpViewModelBinding()
    }

    private fun init(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        _binding = DataBindingUtil.inflate(
            inflater,
            setContent(),
            container,
            false
        )
        _viewModel =
            if (useSharedViewModel) ViewModelProvider(requireActivity())[provideViewmodel()]
            else ViewModelProvider(this)[provideViewmodel()]
    }

    private fun setLifecycle(
        binding: VDBinding,
        viewModel: ViewModel
    ) {
        binding.run {
            setVariable(BR.viewmodel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}