package com.skylight.android.volunteering.app.api

import com.skylight.android.volunteering.app.util.MConstants.FleetType.TAXI
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 24-02-2022 at 19:06.
 * Mocking webservice
 */

@RunWith(JUnit4::class)
class ApiServiceTest : BaseServiceTest() {

    private lateinit var service: ApiService

    @Before
    @Throws(IOException::class)
    fun createService() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getApiSource() = runBlocking {
        enqueueResponse("fleet_response.json")
        val response = service.getFleetLocation("", "", "", "", "")

        // Dummy request
        mockWebServer.takeRequest()

        // Check api source
        assertThat(response, notNullValue())
        assertThat(response.poiList, notNullValue())
        assertThat(response.poiList?.size, `is`(30))

        // Check list
        val vehicles = response.poiList

        // Check item 1
        val item = vehicles?.get(0)
        assertThat(item, notNullValue())
        assertThat(item?.id, `is`(636424))
        assertThat(item?.fleetType, `is`(TAXI.name))
        assertThat(item?.heading, `is`(130.31669311918807))

        // check coordinated
        val coordinate = item?.coordinate
        assertThat(coordinate, notNullValue())
        assertThat(coordinate?.latitude, `is`(53.553420026204506))
        assertThat(coordinate?.longitude, `is`(9.816601870885107))
    }
}