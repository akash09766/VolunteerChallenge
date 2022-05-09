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
import com.skylight.android.volunteering.databinding.DummyUserChooserLayoutBinding
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class DummyUserChooserFragment : BaseFragment(R.layout.dummy_user_chooser_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        DummyUserChooserFragment::class.java.simpleName
    }

    private val binding by viewBinding(DummyUserChooserLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventOrganiserFlowBtn.setOnClickListener {
            gotoCreateEventFragment()
        }

        binding.userLoginBtn.setOnClickListener {
            gotoEventsListFragment()
        }

        val db = Firebase.firestore

        val docRef = db.collection(MConstants.DataHolderKeys.ADMINS.name)
            .document("admin_list")
        val adminInfo = AdminInfo()
        adminInfo.apply {
            name = "Akash wangalwar"
            phone = "987778377883"
            isSuperAdmin = true
            emailId = "wangalwar.akash@gmail.com"
            dateTime = Date()
        }
        val admin = AdminList()
        admin.adminInfo = ArrayList()
        admin.adminInfo!!.add(adminInfo)
        Timber.d("$TAG  onViewCreated() called ")
        docRef.addSnapshotListener { value, error ->
            Timber.d("$TAG  onViewCreated() called with: value = $value, error = $error")
            if (value!!.exists().not()) {
                Timber.d("$TAG adding data to server")
                docRef.set(
                    admin
                ).addOnSuccessListener {
                    Timber.d("$TAG successfully added data to server")
                }.addOnFailureListener {
                    Timber.e("$TAG error while adding data to server : ",it)
                }
            } else {
                Timber.d("$TAG already data there on server")
            }
        }

    }

    private fun gotoCreateEventFragment() {
        val db = Firebase.firestore

        val docRef = db.collection(MConstants.DataHolderKeys.ORGANISATIONS.name)
            .document(MConstants.ORG_ID)

        docRef.addSnapshotListener { value, error ->
            if (value!!.exists().not()) {
                db.collection(MConstants.DataHolderKeys.ORGANISATIONS.name)
                    .document(MConstants.ORG_ID).set(
                        OrganisationInfo()
                    )
            }
        }

        Navigation.findNavController(binding.eventOrganiserFlowBtn)
            .navigate(DummyUserChooserFragmentDirections.actionDummyUserChooserFragmentToCreateEventFragment())
    }

    private fun gotoEventsListFragment() {
        val db = Firebase.firestore

        val volunteerItem = VolunteerItem()
        volunteerItem.apply {
            volunteerAge = "31"
            volunteerEmail = "wangalwar.akash@gmail.com"
            volunteerName = "Akash Wangalwar"
            volunteerPhone = "9766795979"
            volunteerAge = "31"
            aboutVolunteer = "Work at IBM as Android Developer"
            socialMediaInfo = "akash@9766795979 -> twitter"
        }

        val docRef = db.collection(MConstants.DataHolderKeys.VOLUNTEERS.name)
            .document(volunteerItem.volunteerEmail!!)

        docRef.addSnapshotListener { value, error ->
            if (value!!.exists().not()) {
                db.collection(MConstants.DataHolderKeys.VOLUNTEERS.name)
                    .document(volunteerItem.volunteerEmail!!).set(
                        volunteerItem
                    )
            }
        }

        Navigation.findNavController(binding.eventOrganiserFlowBtn)
            .navigate(DummyUserChooserFragmentDirections.actionDummyUserChooserFragmentToEventsListFragment())
    }
}