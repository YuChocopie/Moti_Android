package com.ahobsu.moti.presentation.ui.mypage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ahobsu.moti.R
import com.ahobsu.moti.data.injection.Injection
import com.ahobsu.moti.databinding.FragmentMypageBinding
import com.ahobsu.moti.presentation.BaseFragment
import com.ahobsu.moti.presentation.ui.main.MainActivity


class MyPageFragment :
    BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            MyPageViewModelFactory(Injection.provideUserRepository())
        ).get(MyPageViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        binding.btnMypageEdit.setOnClickListener {
            (activity as MainActivity).changeFragment(MyPageEditFragment)

        }
    }

    companion object {
        fun newInstance() =
            MyPageFragment()
    }

}