package com.skylight.android.volunteering.app.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.EventInfo
import com.skylight.android.volunteering.app.model.event.SessionSpeakersItem
import com.skylight.android.volunteering.app.model.event.SessionsItem
import com.skylight.android.volunteering.app.util.MConstants.DataHolderKeys
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getBackStackData
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.SessionDetailsLayoutBinding
import timber.log.Timber

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 03:46.
 * Simple fragment where all the vehicles data will be shown in list format
 */
class CreateSessionFragment : BaseFragment(R.layout.session_details_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        CreateSessionFragment::class.java.simpleName
    }

    private val binding by viewBinding(SessionDetailsLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }
    private val speakerList = ArrayList<SessionSpeakersItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSessionBtn.setOnClickListener {
//            val data = (dataCache.get(DataHolderKeys.Events.name) as EventInfo) ?: EventInfo()
            val data: EventInfo = if ((dataCache.get(DataHolderKeys.Events.name)) == null) {
                EventInfo()
            } else {
                (dataCache.get(DataHolderKeys.Events.name) as EventInfo)
            }

            val sessions: ArrayList<SessionsItem?>? =
                (data.sessions ?: ArrayList<SessionsItem>()) as ArrayList<SessionsItem?>?

            val sessionsItem = SessionsItem()
            sessionsItem.apply {
                sessionName = binding.sessionTitleEt.text.toString()
                sessionDate = binding.sessionDateEt.text.toString()
                sessionTime = binding.sessionTimeEt.text.toString()
                sessionAddress = binding.sessionAddressEt.text.toString()
                otherDetails = binding.sessionOtherDetailsEt.text.toString()
                sessionSpeakers = speakerList
            }
            sessions?.add(sessionsItem)
            data.sessions = sessions

            dataCache.put(DataHolderKeys.Events.name, data as Object)
            closeThisFragment()
        }

        binding.addSpeakersBtn.setOnClickListener {
            gotoAddSpeakerFragment()
        }

        getBackStackData<SessionSpeakersItem>(DataHolderKeys.SPEAKER_DATA.name) { data ->
            speakerList.add(data)
            Timber.d("$TAG speakerList --> $speakerList")
        }
    }

    private fun gotoAddSpeakerFragment() = Navigation.findNavController(binding.addSessionBtn)
        .navigate(CreateSessionFragmentDirections.actionCreateSessionFragmentToAddSpeakerFragment())


    private fun closeThisFragment() {
        Navigation.findNavController(binding.addSessionBtn).popBackStack()
    }
}
