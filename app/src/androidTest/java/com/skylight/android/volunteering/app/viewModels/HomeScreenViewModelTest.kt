package com.skylight.android.volunteering.app.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skylight.android.volunteering.app.dataSource.DataRepository
import com.skylight.android.volunteering.core.di.GoogleServiceModule
import com.skylight.android.volunteering.core.di.NetworkServiceModule
import junit.framework.TestCase
import com.skylight.android.volunteering.app.util.MConstants.point1
import com.skylight.android.volunteering.app.util.MConstants.point2
import com.skylight.android.volunteering.core.ui.ViewState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 24-02-2022 at 20:54.
 */

@RunWith(AndroidJUnit4::class)
class HomeScreenViewModelTest : TestCase() {

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val okHttpClient = NetworkServiceModule.provideOkHttpClient(context)
        val retrofit = NetworkServiceModule.provideRetrofit(okHttpClient)
        val apiService = NetworkServiceModule.provideApiService(retrofit)

        val retrofitGoogle = NetworkServiceModule.provideRetrofit(okHttpClient)
        val googleService = GoogleServiceModule.provideGoogleService(retrofitGoogle)

        val dataRepository = DataRepository.DefaultDataRepository(apiService, googleService)

        homeScreenViewModel = HomeScreenViewModel(dataRepository)
    }

    @Test
    fun testHomeScreenViewModel_loading_start() {
        //WHEN
        homeScreenViewModel.getHomeScreenData(
            point1.first,
            point1.second,
            point2.first,
            point2.second
        )

        val response = homeScreenViewModel._fleetLocationResponse.getOrAwaitValue(1)

        assertTrue(response is ViewState.Loading)

        //THEN
        when (response) {
            is ViewState.Success -> {

            }
            is ViewState.Loading -> {
                assertTrue(response.data)
            }
            is ViewState.Error -> {
            }
        }
    }


    @Test
    fun testHomeScreenViewModel_loading_end() {
        //WHEN
        homeScreenViewModel.getHomeScreenData(
            point1.first,
            point1.second,
            point2.first,
            point2.second
        )

        val response = homeScreenViewModel._fleetLocationResponse.getOrAwaitValue(2)

        //THEN
        when (response) {
            is ViewState.Success -> {
            }
            is ViewState.Loading -> {
                assertFalse(response.data)
            }
            is ViewState.Error -> {
            }
        }
    }


    @Test
    fun testHomeScreenViewModel_Success_Data() {
        //WHEN
        homeScreenViewModel.getHomeScreenData(
            point1.first,
            point1.second,
            point2.first,
            point2.second
        )

        val response = homeScreenViewModel._fleetLocationResponse.getOrAwaitValue(3)

        //THEN
        when (response) {
            is ViewState.Success -> {
                assertTrue(response.data.poiList!!.isNotEmpty())
            }
            is ViewState.Loading -> {
            }
            is ViewState.Error -> {
            }
        }
    }

    /** NOTE :
     * Run this test after disconnecting device from internet , to get error msg back from data repo
     * */
    @Test
    fun testHomeScreenViewModel_Error() {
        //WHEN
        homeScreenViewModel.getHomeScreenData(
            point1.first,
            point1.second,
            point2.first,
            point2.second
        )

        val response = homeScreenViewModel._fleetLocationResponse.getOrAwaitValue(3)

        //THEN
        when (response) {
            is ViewState.Success -> {
            }
            is ViewState.Loading -> {
            }
            is ViewState.Error -> {
                assertTrue(response.message.isNotBlank())
            }
        }
    }
}