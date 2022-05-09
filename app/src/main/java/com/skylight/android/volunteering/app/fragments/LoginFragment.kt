package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.AdminInfo
import com.skylight.android.volunteering.app.model.event.AdminList
import com.skylight.android.volunteering.app.model.event.VolunteerItem
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.LoginFragmentLayoutBinding
import timber.log.Timber
import java.util.*

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class LoginFragment : BaseFragment(R.layout.login_fragment_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        LoginFragment::class.java.simpleName
    }

    private val binding by viewBinding(LoginFragmentLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            performLogin(MConstants.LOGGED_IN_USER_EMAIL_ID)
        }
    }

    @SuppressLint("TimberArgCount")
    private fun performLogin(userEmailId: String) {

        val db = Firebase.firestore
        db.collection(MConstants.DataHolderKeys.ADMINS.name).document("admin_list").get()
            .addOnSuccessListener { documents ->
                val adminList = documents.toObject(AdminList::class.java)

                adminList?.adminInfo?.forEach {
                    Timber.d("$TAG admins list item ->:  ${it}")
                    if (it.emailId.equals(userEmailId, true)) {
                        continueAdminFlow(it, userEmailId)
                        updateLastLoggedDataInCloud(it, adminList)
                        return@addOnSuccessListener
                    }
                }

                continueVolunteerFlow(userEmailId)
            }.addOnFailureListener {
                Timber.e("$TAG performLogin() -> error while fetching admins list :  ${it.message}")
            }

    }

    @SuppressLint("TimberArgCount")
    private fun updateLastLoggedDataInCloud(adminInfo: AdminInfo, adminList: AdminList) {

        adminList.adminInfo?.find {
            it.emailId.equals(adminInfo.emailId, true)
        }?.lastLoggedInOn = Date()

        val db = Firebase.firestore
        val docRef = db.collection(MConstants.DataHolderKeys.ADMINS.name).document("admin_list")

        db.runTransaction {
            it.set(docRef, adminList)
        }.addOnSuccessListener {
            Timber.d("$TAG addOnSuccessListener ::")
        }.addOnFailureListener {
            Timber.e("$TAG updateLastLoggedDataInCloud() -> error while fetching admins list : ${it.message}")
        }
    }

    private fun continueAdminFlow(adminInfo: AdminInfo, userEmailId: String) {
        Timber.d(
            "$TAG continueAdminFlow() called with: adminInfo = $adminInfo, userEmailId = $userEmailId"
        )
        prefs.saveAdminUserData(adminInfo)
        gotoAdminHomeScreenFragment()
    }

    private fun gotoAdminHomeScreenFragment() = Navigation.findNavController(binding.root)
        .navigate(LoginFragmentDirections.actionLoginFragmentToAdminHomeScreenFragment())

    private fun continueVolunteerFlow(userEmailId: String) {
        Timber.d("$TAG continueVolunteerFlow() called with: userEmailId = $userEmailId")

        val db = Firebase.firestore

        db.collection(MConstants.DataHolderKeys.VOLUNTEERS.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.id.equals(userEmailId, true)) {
                        val volunteerData = document.toObject(VolunteerItem::class.java)
                        saveDataInDBAndGotoHomeScreen(volunteerData)
                        return@addOnSuccessListener
                    }
                }
                gotoCreateVolunteerScreen(userEmailId)
            }.addOnFailureListener { exception ->
                Timber.e("$TAG Error adding document ${exception.message}")
            }
    }

    private fun gotoCreateVolunteerScreen(userEmailId: String) =
        Navigation.findNavController(binding.root)
            .navigate(
                LoginFragmentDirections.actionLoginFragmentToCreateVolunteerFragment(
                    userEmailId
                )
            )

    private fun saveDataInDBAndGotoHomeScreen(volunteerData: VolunteerItem) {
        Timber.d("$TAG saveDataInDBandGotoHomeScreen() called with: volunteerData = $volunteerData")
        prefs.saveVolunteerUserData(volunteerData)
        gotoEventsListFragment()
    }

    private fun gotoEventsListFragment() = Navigation.findNavController(binding.root)
        .navigate(LoginFragmentDirections.actionLoginFragmentToEventsListFragment())

}
/* how add item to list using doc ref

        val db = Firebase.firestore

        val adminItemDBRef =
            db.collection(MConstants.DataHolderKeys.ADMINS.name).document("admin_list")

        adminList.adminInfo?.find {
            it.emailId.equals(adminInfo.emailId, true)
        }?.lastLoggedInOn = Date()

        adminItemDBRef.update("adminInfo", FieldValue.arrayUnion(adminInfo)).addOnSuccessListener {
            Timber.d("$TAG addOnSuccessListener ::")
        }.addOnFailureListener {
            Timber.e("$TAG updateLastLoggedDataInCloud() -> error while fetching admins list : ${it.message}")
        }

 ------------ another way --------------

        val adminItemDBRef =
            db.collection(MConstants.DataHolderKeys.ADMINS.name).document("admin_list")

        adminList.adminInfo?.find {
            it.emailId.equals(adminInfo.emailId, true)
        }?.lastLoggedInOn = Date()

        val data: MutableMap<String, Any> = HashMap()
        data["adminInfo"] = FieldValue.arrayUnion(adminInfo)

        adminItemDBRef.update(data).addOnSuccessListener {
            Timber.d("$TAG addOnSuccessListener ::")
        }.addOnFailureListener {
            Timber.e("$TAG updateLastLoggedDataInCloud() -> error while fetching admins list : ${it.message}")
        }
*/

