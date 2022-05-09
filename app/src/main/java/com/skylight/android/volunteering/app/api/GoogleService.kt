package com.skylight.android.volunteering.app.api

import retrofit2.Response
import retrofit2.http.GET

/**
 * ping to google to check if the connected internet is working or not. (only if you get error from regular api endpoint)
 */
interface GoogleService {

    /**
     * ping to google to check if the connected internet is working or not.
     */
    @GET("/")
    suspend fun getGoogle(): Response<String>

}

