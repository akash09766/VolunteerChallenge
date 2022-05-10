package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.adapter.SessionNumberListAdapter
import com.skylight.android.volunteering.app.adapter.SpeakerListAdapter
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.model.event.*
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.EventDetailsScreenLayoutBinding
import timber.log.Timber

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 20-04-2022 at 22:58.
 */
class EventDetailsFragment : BaseFragment(R.layout.event_details_screen_layout),
    ListItemClickListener<Int> {

    @Suppress("unused")
    private val TAG by lazy {
        EventDetailsFragment::class.java.simpleName
    }

    val args: EventDetailsFragmentArgs by navArgs()

    private val binding by viewBinding(EventDetailsScreenLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }
    private lateinit var mSessionNumberListAdapter: SessionNumberListAdapter

    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$TAG args : ${args.eventInfoDetails}")

        if(args.eventInfoDetails.isVolunteeredForThisEvent){
            binding.volunteerBtn.text = "Volunteered"
            binding.volunteerBtn.isEnabled = false
        }else{
            binding.volunteerBtn.setOnClickListener {
                getLoggedInVolunteerData()
            }
        }

        binding.eventName.text = args.eventInfoDetails.eventName
        binding.orgName.text = "Organised by ${args.eventInfoDetails.organisationName}"
        binding.voluteerCount.text = "${args.eventInfoDetails.volunteer_list?.size ?: 0} Volunteered"
        binding.eventAddress.text = "${args.eventInfoDetails.address}"
        binding.language.text = args.eventInfoDetails.sessionLanguage
        binding.eventDetails.text = args.eventInfoDetails.eventDetails

        binding.eventMode.text = args.eventInfoDetails.evenMode

        if (args.eventInfoDetails.sessions != null && args.eventInfoDetails.sessions!!.size > 0) {
            mSessionNumberListAdapter = SessionNumberListAdapter(
                args.eventInfoDetails.sessions!!.size,
                listener = this@EventDetailsFragment
            )

            binding.sessionList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = mSessionNumberListAdapter
            }

            setDataOfSessions(args.eventInfoDetails.sessions!![0])
        }
    }

    private fun getLoggedInVolunteerData() {

        val volunteerList = ArrayList<VolunteerItem?>()
        var volunteerItem: VolunteerItem? = null

        val db = Firebase.firestore

        db.collection(MConstants.DataHolderKeys.VOLUNTEERS.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    volunteerList.add(document.toObject(VolunteerItem::class.java))
                }

                Timber.d("$TAG volunteerList size ---******************---> ${volunteerList.size}")
                volunteerList.forEach { item ->
                    Timber.d("$TAG volunteer Name :-->  ${item?.volunteerEmail}")
                    if (item?.volunteerEmail.equals(prefs.getVolunteerUserData()?.volunteerEmail!!)) {
                        volunteerItem = item
                        return@forEach
                    }
                }
                addUserToThisEvent(volunteerItem!!)
            }
            .addOnFailureListener { exception ->
                Timber.e("$TAG Error adding document ${exception.message}")
            }
    }

    private fun addUserToThisEvent(volunteerItem: VolunteerItem?) {

        var eventItem: EventInfo? =args.eventInfoDetails

        val db = Firebase.firestore

        val orgDocRef =
            db.collection(MConstants.DataHolderKeys.ORGANISATIONS.name).document(eventItem?.organisationId!!)

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
            binding.volunteerBtn.text = "Volunteered"
            binding.volunteerBtn.isEnabled = false
        }.addOnFailureListener {
            Timber.e("$TAG Error adding document : ${it.message}")
        }
    }

    private fun setDataOfSessions(sessionsItem: SessionsItem?) {
        binding.sessionName.text = sessionsItem?.sessionName
        binding.sessionAddress.text = sessionsItem?.sessionAddress
        binding.sessionAbout.text = sessionsItem?.otherDetails

        if (sessionsItem?.sessionSpeakers != null && sessionsItem.sessionSpeakers!!.size > 0) {

            binding.sessionSpeakerList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = SpeakerListAdapter(sessionsItem.sessionSpeakers!!)
            }
        }
    }

    override fun onItemClick(pos: Int) {
        mSessionNumberListAdapter.setSelectedItem(pos)
        mSessionNumberListAdapter.notifyDataSetChanged()
        setDataOfSessions(args.eventInfoDetails.sessions!![pos])
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Event Details"
    }
}