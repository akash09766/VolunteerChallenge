package com.skylight.android.volunteering.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.skylight.android.volunteering.databinding.HomeScreenFragmentLayoutBinding
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.FleetLocationResponse
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.core.utils.visible

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 03:46.
 * Simple fragment where all the vehicles data will be shown in list format
 */
class HomeScreenFragment : BaseFragment(R.layout.home_screen_fragment_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        HomeScreenFragment::class.java.simpleName
    }

    private lateinit var response: FleetLocationResponse

    private val binding by viewBinding(HomeScreenFragmentLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewToView()
        binding.eventModeEt.visible()
    }

    private fun setViewToView() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.event_modes)
        )
        binding.eventModeEt.setAdapter(adapter)
    }
}
