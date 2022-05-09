package com.skylight.android.volunteering.app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * POJO class for vehicle list api response
 */

@Parcelize
data class FleetLocationResponse(

    @field:SerializedName("poiList")
    val poiList: List<PoiListItem?>? = ArrayList<PoiListItem>()
) : Parcelable

@Parcelize
data class PoiListItem(

    @field:SerializedName("coordinate")
    val coordinate: Coordinate? = null,

    @field:SerializedName("fleetType")
    val fleetType: String? = null,

    @field:SerializedName("heading")
    val heading: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable

@Parcelize
data class Coordinate(

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null
) : Parcelable
