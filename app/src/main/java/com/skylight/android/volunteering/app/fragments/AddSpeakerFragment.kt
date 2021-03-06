package com.skylight.android.volunteering.app.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.SessionSpeakersItem
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.setBackStackData
import com.skylight.android.volunteering.core.utils.showLongToast
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.SpeakerInfoLayoutBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 03:46.
 * Simple fragment where all the vehicles data will be shown in list format
 */
class AddSpeakerFragment : BaseFragment(R.layout.speaker_info_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        AddSpeakerFragment::class.java.simpleName
    }

    private val binding by viewBinding(SpeakerInfoLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSpeakerBtn.setOnClickListener {
            if(checkInputDataEmpty()){
                showLongToast("Please Input Details")
                return@setOnClickListener
            }
            val speakersItem = SessionSpeakersItem()
            speakersItem.apply {
                speakerName = binding.speakerNameEt.text.toString().trim()
                speakerAge = binding.speakerAgeEt.text.toString().trim()
                speakerQualification = binding.speakerQualificationEt.text.toString().trim()
                speakerQualification = binding.speakerQualificationEt.text.toString().trim()
                socialMediaInfo = binding.speakerSocialMediaUsernameEt.text.toString().trim()
                aboutSpeaker = binding.aboutSpeakerEt.text.toString().trim()
            }

            setBackStackData(MConstants.DataHolderKeys.SPEAKER_DATA.name, speakersItem)
        }
    }

    fun checkInputDataEmpty(): Boolean{
       return  binding.speakerNameEt.text.toString().trim().isEmpty() ||
                binding.speakerAgeEt.text.toString().trim().isEmpty() ||
                binding.speakerQualificationEt.text.toString().trim().isEmpty() ||
                binding.speakerSocialMediaUsernameEt.text.toString().trim().isEmpty() ||
                binding.aboutSpeakerEt.text.toString().trim().isEmpty()

    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Add Speaker"
    }
}
