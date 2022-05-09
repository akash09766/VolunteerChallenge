package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.VolunteerItem
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.showLongSnackBar
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.CreateVolunteerScreenLayoutBinding
import timber.log.Timber
import java.util.*

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class CreateVolunteerFragment : BaseFragment(R.layout.create_volunteer_screen_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        CreateVolunteerFragment::class.java.simpleName
    }

    val args: CreateVolunteerFragmentArgs by navArgs()

    private val binding by viewBinding(CreateVolunteerScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailEt.setText(args.userEmailId)
        binding.createVolunteerBtn.setOnClickListener {
            getAllVolunteerData(args.userEmailId)
        }
    }

    private fun getAllVolunteerData(email: String?) {
        Timber.d("$TAG email : $email")
        val volunteerItem = VolunteerItem()
        volunteerItem.apply {
            dateTime = Date()
            volunteerName = binding.nameEt.text.toString().trim()
            volunteerEmail = binding.emailEt.text.toString().trim()
            volunteerPhone = binding.phoneEt.text.toString().trim()
            volunteerAge = binding.ageEt.text.toString().trim()
            volunteerQualification = binding.qualificationEt.text.toString().trim()
            aboutVolunteer = binding.aboutVolunteerEt.text.toString().trim()
            socialMediaInfo = binding.socialMediaInfoEt.text.toString().trim()
        }
        val db = Firebase.firestore

        val docRef = db.collection(MConstants.DataHolderKeys.VOLUNTEERS.name)
            .document(email!!)

        docRef.get().addOnSuccessListener { doc ->
            Timber.d("$TAG  onViewCreated() addOnSuccessListener ${doc.exists()}")
            if (doc.exists().not()) {
                docRef.set(volunteerItem).addOnSuccessListener {
                    showLongSnackBar(
                        binding.createVolunteerBtn,
                        getString(R.string.volunteer_added_successfully_user_msg)
                    )
                    prefs.saveVolunteerUserData(volunteerItem)
                    gotoEventsListFragment()
                }.addOnFailureListener {
                    showLongSnackBar(
                        binding.createVolunteerBtn,
                        getString(R.string.volunteer_unable_to_add_user_msg)
                    )
                    Timber.d("$TAG onViewCreated() unable to add data to server : ${it.message}")
                }
            } else {
                showLongSnackBar(
                    binding.createVolunteerBtn,
                    getString(R.string.volunteer_already_exists_user_msg)
                )
                Timber.d("$TAG onViewCreated() already data there on server")
            }
        }.addOnFailureListener {
            showLongSnackBar(
                binding.createVolunteerBtn,
                getString(R.string.volunteer_unable_to_add_user_msg)
            )
            Timber.d("$TAG  onViewCreated() addOnFailureListener : ${it.message}")
        }
    }

    private fun gotoEventsListFragment() {
        Navigation.findNavController(binding.createVolunteerBtn)
            .navigate(CreateVolunteerFragmentDirections.actionCreateVolunteerFragmentToEventsListFragment())
    }
}