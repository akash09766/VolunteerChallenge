package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.OrganisationInfo
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.showLongSnackBar
import com.skylight.android.volunteering.core.utils.showLongToast
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.CreateOrganisationFragmentLayoutBinding
import timber.log.Timber
import java.util.*

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 08-04-2022 at 03:46.
 */
class CreateOrganisationFragment : BaseFragment(R.layout.create_organisation_fragment_layout) {


    @Suppress("unused")
    private val TAG by lazy {
        CreateOrganisationFragment::class.java.simpleName
    }

    private val binding by viewBinding(CreateOrganisationFragmentLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createOrgBtn.setOnClickListener {
            if(checkInputDataEmpty()){
                showLongToast("Please input Details")
                return@setOnClickListener
            }
            getAllOrganisationData(binding.emailEt.text.toString().trim())
        }
    }

    private fun checkInputDataEmpty(): Boolean{
       return  binding.nameEt.text.toString().trim().isEmpty() ||
               binding.emailEt.text.toString().trim().isEmpty() ||
               binding.phoneEt.text.toString().trim().isEmpty() ||
               binding.cityEt.text.toString().trim().isEmpty() ||
               binding.addressEt.text.toString().trim().isEmpty()

    }
    private fun getAllOrganisationData(email: String?) {
        val organisationInfo = OrganisationInfo()
        organisationInfo.apply {
            dateTime = Date()
            name = binding.nameEt.text.toString().trim()
            this.email = binding.emailEt.text.toString().trim()
            phone = binding.phoneEt.text.toString().trim()
            city = binding.cityEt.text.toString().trim()
            address = binding.addressEt.text.toString().trim()
        }
        val db = Firebase.firestore

        val docRef = db.collection(MConstants.DataHolderKeys.ORGANISATIONS.name)
            .document(email!!)

        docRef.get().addOnSuccessListener { doc->
            Timber.d("$TAG  onViewCreated() addOnSuccessListener ${doc.exists()}")
            if(doc.exists().not()){
                docRef.set(organisationInfo).addOnSuccessListener {
                    showLongSnackBar(
                        binding.createOrgBtn,
                        getString(R.string.org_added_successfully_user_msg)
                    )
                    Navigation.findNavController(binding.createOrgBtn).popBackStack()
                }.addOnFailureListener {
                    showLongSnackBar(
                        binding.createOrgBtn,
                        getString(R.string.org_unable_to_add_user_msg)
                    )
                    Timber.d("$TAG onViewCreated() unable to add data to server : ${it.message}")
                }
            }else{
                showLongSnackBar(
                    binding.createOrgBtn,
                    getString(R.string.org_already_exists_user_msg)
                )
                Timber.d("$TAG onViewCreated() already data there on server")
            }
        }.addOnFailureListener {
            showLongSnackBar(
                binding.createOrgBtn,
                getString(R.string.org_unable_to_add_user_msg)
            )
            Timber.d("$TAG  onViewCreated() addOnFailureListener : ${it.message}")
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Add Organisation"
    }
}