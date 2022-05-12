package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.activities.MainActivity
import com.skylight.android.volunteering.app.adapter.EventListAdapter
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.model.event.EventInfo
import com.skylight.android.volunteering.app.model.event.OrganisationInfo
import com.skylight.android.volunteering.app.model.event.VolunteerItem
import com.skylight.android.volunteering.app.util.MConstants.DataHolderKeys
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.gone
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.core.utils.visible
import com.skylight.android.volunteering.databinding.EventsListScreenLayoutBinding
import timber.log.Timber

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 04-04-2022 at 13:56.
 */
class EventsListFragment : BaseFragment(R.layout.events_list_screen_layout),
    ListItemClickListener<EventInfo> {

    private lateinit var loggedInVoluteerEmailId: String

    private lateinit var eventListAdapter: EventListAdapter

    @Suppress("unused")
    private val TAG by lazy {
        EventsListFragment::class.java.simpleName
    }

    private val binding by viewBinding(EventsListScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }
    val eventList = ArrayList<EventInfo?>()

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        loggedInVoluteerEmailId = prefs.getVolunteerUserData()?.volunteerEmail!!

        setDataToRecylerview()

        /*db.collection(DataHolderKeys.Events.name)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection(DataHolderKeys.Events.name).document(document.id).get().addOnSuccessListener {
                        val item = it.toObject<EventInfo>()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Timber.e("$TAG Error adding document", exception)
            }*/
    }

    private fun setDataToRecylerview() {

        val db = Firebase.firestore
        val orgList = ArrayList<OrganisationInfo?>()
        db.collection(DataHolderKeys.ORGANISATIONS.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    orgList.add(document.toObject(OrganisationInfo::class.java))
                }
                eventList.clear()
                orgList.forEach { orgItem ->
                    if (orgItem?.events_list.isNullOrEmpty().not()) {
                        eventList.addAll(orgItem?.events_list!!)
                    }
                }

                eventList.forEach { eventItem ->
                    if (eventItem?.volunteer_list.isNullOrEmpty().not()) {
                        eventItem?.volunteer_list?.forEach inner@{ volunteerItem ->
                            if (volunteerItem?.volunteerEmail.equals(
                                    loggedInVoluteerEmailId,
                                    true
                                )
                            ) {
                                eventItem.isVolunteeredForThisEvent = true
                                return@inner
                            }
                        }
                    }
                }
                if (eventList.isEmpty()) {
                    binding.eventList.gone()
                    binding.emptyListMsg.visible()
                } else {
                    binding.eventList.visible()
                    binding.emptyListMsg.gone()

                    eventListAdapter = EventListAdapter(eventList, this@EventsListFragment)

                    binding.eventList.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = eventListAdapter
                    }
                }

                Timber.d("$TAG eventList size : ${eventList.size}")
            }
            .addOnFailureListener { exception ->
                Timber.e("$TAG Error adding document : ${exception.message}")
            }
    }

    override fun onItemClick(item: EventInfo) {
        Timber.d("$TAG onItemClick() called with: item = $item")

        Navigation.findNavController(binding.root)
            .navigate(
                EventsListFragmentDirections.actionEventsListFragmentToEventDetailsFragment(
                    item
                )
            )
//        getLoggedInVolunteerData(position)
    }

    private fun getLoggedInVolunteerData(selectedItemPosition: Int) {

        val volunteerList = ArrayList<VolunteerItem?>()
        var volunteerItem: VolunteerItem? = null

        val db = Firebase.firestore

        db.collection(DataHolderKeys.VOLUNTEERS.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    volunteerList.add(document.toObject(VolunteerItem::class.java))
                }

                Timber.d("$TAG volunteerList size ---******************---> ${volunteerList.size}")
                volunteerList.forEach { item ->
                    Timber.d("$TAG volunteer Name :-->  ${item?.volunteerEmail}")
                    if (item?.volunteerEmail.equals(loggedInVoluteerEmailId)) {
                        volunteerItem = item
                        return@forEach
                    }
                }
                addVolutnteerToEvent(volunteerItem!!, selectedItemPosition)
            }
            .addOnFailureListener { exception ->
                Timber.e("$TAG Error adding document ${exception.message}")
            }
    }

    private fun addVolutnteerToEvent(volunteerItem: VolunteerItem?, selectedItemPosition: Int) {

        var eventItem: EventInfo? = eventList[selectedItemPosition]

        val db = Firebase.firestore

        val orgDocRef =
            db.collection(DataHolderKeys.ORGANISATIONS.name).document(eventItem?.organisationId!!)

        db.runTransaction(Transaction.Function {

            val organisationInfoItem = it.get(orgDocRef).toObject(OrganisationInfo::class.java)
            organisationInfoItem?.events_list?.forEach { item ->
                if (item?.eventId!!.equals(eventItem!!.eventId)) {
                    if (item.volunteer_list.isNullOrEmpty()) {
                        item.volunteer_list = ArrayList<VolunteerItem?>()
                        item.volunteer_list!!.add(volunteerItem)
                    } else {
                        item.volunteer_list!!.add(volunteerItem)
                    }
                }
            }

            it.set(orgDocRef, organisationInfoItem!!)
        }).addOnSuccessListener {
            Timber.d("$TAG Transaction completed for addVolutnteerToEvent :")
            refreshList(selectedItemPosition)
        }.addOnFailureListener {
            Timber.e("$TAG Error adding document : ${it.message}")
        }
    }

    private fun refreshList(selectedItemPosition: Int) {
        eventListAdapter.getData()[selectedItemPosition]?.isVolunteeredForThisEvent = true
        eventListAdapter.notifyItemChanged(selectedItemPosition)
        Timber.d("$TAG refreshList()  eventList -> ${Gson().toJson(eventList)}")
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Upcoming Events"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
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
        val mainIntent = Intent(requireActivity(), MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().applicationContext.startActivity(mainIntent)
        activity?.finish()
    }
}