package com.ahobsu.moti.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ahobsu.moti.presentation.BaseFragment
import com.ahobsu.moti.R
import com.ahobsu.moti.databinding.FragmentSignupGenderBinding

class SignUpGenderFragment : BaseFragment<FragmentSignupGenderBinding>(R.layout
    .fragment_signup_gender){

    private val viewModel by viewModels<LoginViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.signUpFragment.observe(
            viewLifecycleOwner, Observer<LoginViewModel.SignUpFragment> {
                if (it == LoginViewModel.SignUpFragment.Birthday) {
                    (activity as LoginActivity?)?.changeFragment(SignUpBirthdayFragment.newInstance())
                }
            })
        viewModel.popFragment.observe(viewLifecycleOwner, Observer<Unit> {
            (activity as LoginActivity?)?.popFragment()
        })
    }

    companion object {
        fun newInstance() = SignUpGenderFragment()
    }

}