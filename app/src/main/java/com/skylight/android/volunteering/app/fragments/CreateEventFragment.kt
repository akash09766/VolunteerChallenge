package com.skylight.android.volunteering.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.google.firebase.firestore.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.model.event.EventInfo
import com.skylight.android.volunteering.app.model.event.OrganisationInfo
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.util.MConstants.DataHolderKeys
import com.skylight.android.volunteering.app.viewModels.HomeScreenViewModel
import com.skylight.android.volunteering.core.ui.base.BaseFragment
import com.skylight.android.volunteering.core.utils.getViewModel
import com.skylight.android.volunteering.core.utils.viewBinding
import com.skylight.android.volunteering.databinding.CreateEventFragmentLayoutBinding
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 03:46.
 * Simple fragment where all the vehicles data will be shown in list format
 */
class CreateEventFragment : BaseFragment(R.layout.create_event_fragment_layout) {

    @Suppress("unused")
    private val TAG by lazy {
        CreateEventFragment::class.java.simpleName
    }

    private val binding by viewBinding(CreateEventFragmentLayoutBinding::bind)
    private val viewModel by lazy { getViewModel<HomeScreenViewModel>() }
    private val mOrgIdList = ArrayList<String?>()


    @SuppressLint("TimberArgCount")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewToView()

        binding.createEventBtn.setOnClickListener {
                   val eventData: EventInfo = if ((dataCache.get(DataHolderKeys.Events.name)) == null) {
                       EventInfo()
                   } else {
                       (dataCache.get(DataHolderKeys.Events.name) as EventInfo)
                   }
                   eventData.apply {
                       eventName = binding.eventNameEt.text.toString().trim()
                       organisationName = binding.organisationNameEt.text.toString().trim()
                       eventDetails = binding.eventDetailsEt.text.toString().trim()
                       evenMode = binding.eventModeEt.text.toString().trim()
                       targetAudience = binding.targetAudienceEt.text.toString().trim()
                       ageGroup = binding.ageGroupEt.text.toString().trim()
                       noOfSeats = binding.noOfSeatsEt.text.toString().trim()
                       sessionLanguage = binding.sessionLanguageEt.text.toString().trim()
                       address = binding.sessionAddressEt.text.toString().trim()
                       isAddressSameForALLSession = binding.isDefaultForAllSessions.isChecked
                       phone = binding.sessionPhoneEt.text.toString().trim()
                       email = binding.sessionEmailEt.text.toString().trim()
                       eventStartDate = binding.eventStartDateEt.text.toString().trim()
                   }

            dataCache.put(DataHolderKeys.Events.name, eventData as Object)
//            val eventData: EventInfo? = loadDataFromJson()
            eventData?.organisationId = binding.organisationNameEt.text.toString()
            Timber.d("$TAG Event Data ------> $eventData")

            val listOfEvents = ArrayList<EventInfo?>()
            listOfEvents.add(eventData!!)

            val db = Firebase.firestore

            val orgDocRef =
                db.collection(DataHolderKeys.ORGANISATIONS.name)
                    .document(binding.organisationNameEt.text.toString())

            db.runTransaction(Transaction.Function {
/*                val hashmap = HashMap<String, Object>()
                hashmap.put("events_list", FieldValue.arrayUnion(listOfEvents) as Object)
                it.set(orgDocRef, hashmap, SetOptions.merge())*/

                val organisationInfoItem = it.get(orgDocRef).toObject(OrganisationInfo::class.java)
                if (organisationInfoItem?.events_list == null) {
                    organisationInfoItem?.events_list = listOfEvents
                } else {
                    organisationInfoItem?.events_list!!.addAll(listOfEvents)
                }
                it.set(orgDocRef, organisationInfoItem!!)
                Timber.d("$TAG eventItem :$organisationInfoItem")
            }).addOnSuccessListener {
                Timber.d("$TAG Transaction success:")
            }.addOnFailureListener {
                Timber.e("$TAG Error adding document : ${it.message}")
            }

            /*
            db.collection(DataHolderKeys.ORGANISATIONS.name)
                .document(eventData?.eventName!!).set(eventData!!)
                .addOnSuccessListener { documentReference ->
                    Timber.d("$TAG DocumentSnapshot added with ID:")
                }
                .addOnFailureListener { e ->
                    Timber.e("$TAG Error adding document", e)
                }*/
        }

        binding.addSessionBtn.setOnClickListener {
            gotoCreateSessionFragment()
        }
    }

    private fun loadDataFromJson(): EventInfo? {
        var json: String? = null
        json = try {
            val inputStream: InputStream = requireActivity().assets.open("Event.json")
            val size: Int = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            inputStream.close()
            String(byteArray, (Charset.forName("utf-8")))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return Gson().fromJson(json, EventInfo::class.java)
    }

    private fun gotoCreateSessionFragment() {
        Navigation.findNavController(binding.createEventBtn)
            .navigate(CreateEventFragmentDirections.actionCreateEventFragmentToCreateSessionFragment())
    }

    private fun setViewToView() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.event_modes)
        )
        binding.eventModeEt.setAdapter(adapter)

        val db = Firebase.firestore
        mOrgIdList.clear()

        val orgDocRef =
            db.collection(DataHolderKeys.ORGANISATIONS.name).get()
                .addOnSuccessListener { documents ->
                    documents.forEach { item ->
                        mOrgIdList.add(item.id)
                    }
                    setDataToOrgNameDropDown()
                }.addOnFailureListener {
                    Timber.d("$TAG setViewToView() error while fetching orgs list : ${it.message}")
                }

    }

    private fun setDataToOrgNameDropDown() {
        Timber.d("$TAG setDataToOrgNameDropDown() mOrgIdList size : ${mOrgIdList.size}")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, mOrgIdList.toArray()
        )

        binding.organisationNameEt.setAdapter(adapter)
    }
}
