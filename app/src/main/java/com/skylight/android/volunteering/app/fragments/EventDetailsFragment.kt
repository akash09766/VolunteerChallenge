package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.adapter.SessionNumberListAdapter
import com.skylight.android.volunteering.app.adapter.SpeakerListAdapter
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.EventDetailsScreenLayoutBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 20-04-2022 at 22:58.
 */
class EventDetailsFragment : BaseFragment(R.layout.event_details_screen_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        EventDetailsFragment::class.java.simpleName
    }

    private val binding by viewBinding(EventDetailsScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.sessionList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SessionNumberListAdapter(15)
        }

        binding.sessionSpeakerList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SpeakerListAdapter(5)
        }
    }
}