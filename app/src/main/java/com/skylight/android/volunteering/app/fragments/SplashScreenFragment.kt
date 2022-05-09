package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.AdminInfo
import com.skylight.android.volunteering.app.model.event.AdminList
import com.skylight.android.volunteering.app.model.event.OrganisationInfo
import com.skylight.android.volunteering.app.model.event.VolunteerItem
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.AdminHomeScreenLayoutBinding
import com.skylight.android.volunteering.databinding.DummyUserChooserLayoutBinding
import com.skylight.android.volunteering.databinding.SplashScreenLayoutBinding
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class SplashScreenFragment : BaseFragment(R.layout.splash_screen_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        SplashScreenFragment::class.java.simpleName
    }

    private val binding by viewBinding(SplashScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (prefs.isUserLoggedIn()) {
            if (prefs.isLoggedInUserAdmin()) {
                gotoAdminHomeScreenFragment()
            } else {
                gotoEventsListFragment()
            }
        } else {
            gotoLoginFragment()
        }
    }

    private fun gotoLoginFragment() {
        Navigation.findNavController(binding.root)
            .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
    }

    private fun gotoAdminHomeScreenFragment() {
        Navigation.findNavController(binding.root)
            .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToAdminHomeScreenFragment())
    }

    private fun gotoEventsListFragment() {
        Navigation.findNavController(binding.root)
            .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToEventsListFragment())
    }
}