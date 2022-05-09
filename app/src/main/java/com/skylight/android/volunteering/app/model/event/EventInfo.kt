package com.skylight.android.volunteering.app.model.event

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import com.google.gson.annotations.SerializedName
import com.skylight.android.volunteering.app.util.MConstants
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventInfo(
    @Exclude
    var isVolunteeredForThisEvent : Boolean = false,

    @field:SerializedName("eventId")
    var eventId: String? = UUID.randomUUID().toString(),

    @field:SerializedName("volunteer_list")
    var volunteer_list: ArrayList<VolunteerItem?>? = null,

    @field:SerializedName("sessions")
    var sessions: ArrayList<SessionsItem?>? = null,

    @field:SerializedName("eventStartDate")
    var eventStartDate: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("ageGroup")
    var ageGroup: String? = null,

    @field:SerializedName("eventDetails")
    var eventDetails: String? = null,

    @field:SerializedName("noOfSeats")
    var noOfSeats: String? = null,

    @field:SerializedName("targetAudience")
    var targetAudience: String? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("eventName")
    var eventName: String? = null,

    @field:SerializedName("OrganisationName")
    var organisationName: String? = null,

    @field:SerializedName("organisationId")
    var organisationId: String? = null,

    @field:SerializedName("sessionLanguage")
    var sessionLanguage: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("evenMode")
    var evenMode: String? = null,

    @field:SerializedName("isAddressSameForALLSession")
    var isAddressSameForALLSession: Boolean? = null
) : Parcelable

@Parcelize
data class SessionsItem(

    @field:SerializedName("sessionId")
    var sessionId: String? = UUID.randomUUID().toString(),

    @field:SerializedName("sessionDate")
    var sessionDate: String? = null,

    @field:SerializedName("otherDetails")
    var otherDetails: String? = null,

    @field:SerializedName("sessionName")
    var sessionName: String? = null,

    @field:SerializedName("sessionAddress")
    var sessionAddress: String? = null,

    @field:SerializedName("sessionTime")
    var sessionTime: String? = null,

    @field:SerializedName("sessionSpeakers")
    var sessionSpeakers: ArrayList<SessionSpeakersItem>? = null
) : Parcelable

@Parcelize
data class SessionSpeakersItem(

    @field:SerializedName("speakerId")
    var speakerId: String? = UUID.randomUUID().toString(),

    @field:SerializedName("speakerQualification")
    var speakerQualification: String? = null,

    @field:SerializedName("speakerName")
    var speakerName: String? = null,

    @field:SerializedName("aboutSpeaker")
    var aboutSpeaker: String? = null,

    @field:SerializedName("speakerAge")
    var speakerAge: String? = null,

    @field:SerializedName("socialMediaInfo")
    var socialMediaInfo: String? = null
) : Parcelable

@Parcelize
data class OrganisationInfo(
    @ServerTimestamp
    @field:SerializedName("dateTime")
    var dateTime: Date? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("city")
    var city: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("events_list")
    var events_list: ArrayList<EventInfo?>? = null,
    @field:SerializedName("organisationId")
    var organisationId: String? = null,
) : Parcelable

@Parcelize
data class VolunteerItem(

    @ServerTimestamp
    @field:SerializedName("dateTime")
    var dateTime: Date? = null,

    @field:SerializedName("isAccountActive")
    var isAccountActive: Boolean? = true,

    @field:SerializedName("volunteerAccountId")
    var volunteerAccountId: String? = UUID.randomUUID().toString(),

    @field:SerializedName("volunteerQualification")
    var volunteerQualification: String? = null,

    @field:SerializedName("volunteerName")
    var volunteerName: String? = null,

    @field:SerializedName("volunteerPhone")
    var volunteerPhone: String? = null,

    @field:SerializedName("volunteerEmail")
    var volunteerEmail: String? = null,

    @field:SerializedName("aboutVolunteer")
    var aboutVolunteer: String? = null,

    @field:SerializedName("volunteerAge")
    var volunteerAge: String? = null,

    @field:SerializedName("socialMediaInfo")
    var socialMediaInfo: String? = null
) : Parcelable

@Parcelize
data class AdminInfo(
    @field:SerializedName("adminAccountId")
    var adminAccountId: String? = UUID.randomUUID().toString(),
    @field:SerializedName("dateTime")
    var dateTime: Date? = null,
    @field:SerializedName("lastLoggedInOn")
    var lastLoggedInOn: Date? = null,
    @field:SerializedName("emailId")
    var emailId: String? = null,
    @field:SerializedName("name")
    var name: String? = null,
    @field:SerializedName("isSuperAdmin")
    var isSuperAdmin: Boolean? = null,
    @field:SerializedName("phone")
    var phone: String? = null
) : Parcelable

@Parcelize
data class AdminList(
    @field:SerializedName("adminInfo")
    var adminInfo: ArrayList<AdminInfo>? = null

) : Parcelable

