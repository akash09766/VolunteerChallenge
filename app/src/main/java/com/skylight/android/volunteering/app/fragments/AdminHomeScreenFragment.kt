package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.iterator
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.activities.MainActivity
import com.skylight.android.volunteering.app.adapter.AdminHomeListAdapter
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.util.ItemOffsetDecoration
import com.skylight.android.volunteering.app.util.MConstants.ADD_ADMIN
import com.skylight.android.volunteering.app.util.MConstants.ADD_ORGANISATION
import com.skylight.android.volunteering.app.util.MConstants.CREATE_EVENT
import com.skylight.android.volunteering.app.util.MConstants.EVENT
import com.skylight.android.volunteering.app.util.MConstants.ORGANISATIONS
import com.skylight.android.volunteering.app.util.MConstants.VOLUNTEER
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.AdminHomeScreenLayoutBinding
import timber.log.Timber
import java.util.*


/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class AdminHomeScreenFragment : BaseFragment(R.layout.admin_home_screen_layout),
    ListItemClickListener<Int> {

    @Suppress("unused")
    private val TAG by lazy {
        AdminHomeScreenFragment::class.java.simpleName
    }

    private val binding by viewBinding(AdminHomeScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }

    val ADMIN_HOME_SCREEN_MENU_OPTIONS = arrayOf(
        "Add Organisation", "Create Event",
        "Add Admin", "Volunteers", "Events",
        "Organisations"
    )

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setDataToViews()
    }

    private fun setDataToViews() {
        binding.menuOptions.apply {
            addItemDecoration(ItemOffsetDecoration(context, R.dimen.dimen_08))
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter =
                AdminHomeListAdapter(ADMIN_HOME_SCREEN_MENU_OPTIONS, this@AdminHomeScreenFragment)
        }
    }

    override fun onItemClick(position: Int) {
        Timber.d("$TAG onItemClick() called with: position = $position")

        when (position) {
            ADD_ORGANISATION -> {
                gotoCreateOrganisationFragment()
            }
            CREATE_EVENT -> {
                gotoCreateEventFragment()
            }
            ADD_ADMIN -> {

            }
            VOLUNTEER -> {

            }
            EVENT -> {

            }
            ORGANISATIONS -> {

            }
        }
    }

    private fun gotoCreateOrganisationFragment() {
        Navigation.findNavController(binding.menuOptions)
            .navigate(AdminHomeScreenFragmentDirections.actionAdminHomeScreenFragmentToCreateOrganisationFragment())
    }

    private fun gotoCreateEventFragment() {
        Navigation.findNavController(binding.menuOptions)
            .navigate(AdminHomeScreenFragmentDirections.actionAdminHomeScreenFragmentToCreateEventFragment())
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.logout) {
            performLogout()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun performLogout() {
        prefs.clearPrefs()
        restartApp()
    }

    private fun restartApp() {
        val mainIntent = Intent(requireActivity(),MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().applicationContext.startActivity(mainIntent)
        activity?.finish()
    }
}