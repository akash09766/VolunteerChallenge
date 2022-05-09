package com.skylight.android.volunteering.app.dataSource

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.skylight.android.volunteering.app.api.ApiService
import com.skylight.android.volunteering.app.api.GoogleService
import com.skylight.android.volunteering.app.dataSource.DataRepository.DefaultDataRepository
import com.skylight.android.volunteering.app.model.Coordinate
import com.skylight.android.volunteering.app.model.FleetLocationResponse
import com.skylight.android.volunteering.app.model.PoiListItem
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.app.util.MConstants.FleetType.*
import com.skylight.android.volunteering.core.ui.ViewState
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import retrofit2.Response


/**
 * Created by Akash Wangalwar.(Github:akash09766) on 24-02-2022 at 02:57.
 */

class DataRepositoryTest : MockitoTest() {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var googleService: GoogleService

    @InjectMocks
    lateinit var defaultDataRepository: DefaultDataRepository

    @Test
    fun `get vehicle from web`() = runBlocking {

        val poiList: List<PoiListItem?>? = ArrayList<PoiListItem>()

        poiList?.toMutableList()?.add(
            PoiListItem(
                fleetType = TAXI.name,
                coordinate = Coordinate(
                    latitude = MConstants.point1.first.toDouble(),
                    MConstants.point1.second.toDouble()
                ),
                heading = 172.121221,
                id = 12973
            )
        )

        poiList?.toMutableList()?.add(
            PoiListItem(
                fleetType = POOLING.name,
                coordinate = Coordinate(
                    latitude = MConstants.point2.first.toDouble(),
                    MConstants.point2.second.toDouble()
                ),
                heading = 90.121221,
                id = 87450
            )
        )

        val fleetLocationResponse = FleetLocationResponse(poiList = poiList)

        //WHEN
        whenever(apiService.getFleetLocation("", "", "", "", "")) doReturn fleetLocationResponse

        val events = arrayOf(
            ViewState.loading(true),
            ViewState.loading(false),
            ViewState.success(fleetLocationResponse)
        )
        //THEN
        assertEquals(
            events.toList(),
            defaultDataRepository.invokeFleetLocationApi("", "", "", "")
                .take(events.size).toList(),
        )

    }

    @Test
    fun `when ping to google is fail`() = runBlocking {
        val error = RuntimeException("Unable to fetch from network")

        //WHEN
        `when`(googleService.getGoogle()).thenThrow(error)

        //THEN
        assertEquals(
            arrayOf(MConstants.TRY_AGAIN).toList(),
            defaultDataRepository.getGooglePing().take(arrayOf(MConstants.TRY_AGAIN).size).toList()
        )
    }

    @Test
    fun `when ping to google is success`() = runBlocking {
        val response = Response.success(MConstants.SUCCESS)

        //WHEN
        `when`(googleService.getGoogle()).thenReturn(response)
        //THEN
        assertEquals(
            arrayOf(MConstants.SUCCESS).toList(),
            defaultDataRepository.getGooglePing().take(arrayOf(MConstants.SUCCESS).size).toList()
        )
    }
}
