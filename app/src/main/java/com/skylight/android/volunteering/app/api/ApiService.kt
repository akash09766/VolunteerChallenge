package com.skylight.android.volunteering.app.api

import com.skylight.android.volunteering.app.model.FleetLocationResponse
import retrofit2.http.*

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 02:59.
 */

/**
 * List of API Calls.
 */
interface ApiService {
    /**
     * get fleet location data from server
     */
    @GET
    suspend fun getFleetLocation(@Url url : String,@Query("p1Lat") p1Lat: String, @Query("p1Lon") p1Lon: String,
                                 @Query("p2Lat") p2Lat: String, @Query("p2Lon") p2Lon: String)
            : FleetLocationResponse
}