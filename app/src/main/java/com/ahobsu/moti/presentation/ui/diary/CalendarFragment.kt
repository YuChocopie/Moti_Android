package com.ahobsu.moti.presentation.ui.diary

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ahobsu.moti.R
import com.ahobsu.moti.data.injection.Injection
import com.ahobsu.moti.databinding.FragmentCalendarBinding
import com.ahobsu.moti.presentation.BaseFragment
import com.ahobsu.moti.presentation.ui.diary.adapter.CalendarAdapter
import com.ahobsu.moti.presentation.ui.main.MainActivity
import com.ahobsu.moti.presentation.ui.main.onBackPressedListener
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar), onBackPressedListener {

    private val TAG = javaClass.simpleName

    lateinit var date: Calendar
    private val calendarAdapter by lazy {
        CalendarAdapter().apply {
            setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
                    //TODO("Not yet implemented")
                }
            })
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            DiaryViewModelFactory(Injection.provideAnswerRepository())
        ).get(DiaryViewModel::class.java)
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        initRecyclerView()
        date = Calendar.getInstance()
        initView()
        viewModel.selectedCalenderMonth.observe(viewLifecycleOwner) {
            when (it) {
                DiaryViewModel.CalenderMonth.NEXT -> {
                    date.run {
                        add(Calendar.MONTH, +1)
                    }
                    initView()
                }
                DiaryViewModel.CalenderMonth.PREVIOUS -> {
                    date.run {
                        add(Calendar.MONTH, -1)
                    }
                    initView()
                }
                DiaryViewModel.CalenderMonth.SELECT -> {
                    viewModel.selectMonth()
                    val datetime = SimpleDateFormat("yyyy.MM", Locale.KOREA).format(date.time)
                    binding.monthSpinner.setMonthPicker(datetime)
                }
            }
        }

        viewModel.selectedMonthBtn.observe(viewLifecycleOwner) {
            val month = binding.monthSpinner.getMonthPicker()
            date.set(month[0], month[1] - 1, 1)
            initView()
        }

        binding.viewCalenderEmpty.setOnClickListener {
            closeFragment()
        }

    }

    fun initView() {
        val datetime = SimpleDateFormat("yyyy.MM", Locale.KOREA).format(date.time)
        binding.tvMonth.text = datetime
        calendarAdapter.replaceAll(date.time)
    }

    private fun initRecyclerView() {
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@CalendarFragment).commit()
            it.popBackStack()
        }
        (activity as MainActivity).deleteSelectedCalenderFragment()
    }

    override fun onBackPressed() {
        closeFragment()
    }
}