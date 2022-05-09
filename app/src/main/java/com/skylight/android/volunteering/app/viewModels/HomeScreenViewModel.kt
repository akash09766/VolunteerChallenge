package com.skylight.android.volunteering.app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skylight.android.volunteering.app.dataSource.DataRepository
import com.skylight.android.volunteering.app.model.FleetLocationResponse
import com.skylight.android.volunteering.core.ui.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] from android architecture component which acts as presenter here and will persist data in
 * case of configuration changes in device
 */

class HomeScreenViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    val TAG = "HomeScreenViewModel"

    private var fleetLocationResponse = MutableLiveData<ViewState<FleetLocationResponse>>()
    val _fleetLocationResponse: LiveData<ViewState<FleetLocationResponse>>
        get() = fleetLocationResponse

    open fun getHomeScreenData(
        p1Lat: String,
        p1Lon: String,
        p2Lat: String,
        p2Lon: String
    ) {
        viewModelScope.launch {
            dataRepository.invokeFleetLocationApi(
                p1Lat = p1Lat,
                p1Lon = p1Lon,
                p2Lat = p2Lat,
                p2Lon = p2Lon
            ).collect { response ->
                fleetLocationResponse.value = response
            }
        }
    }
}

