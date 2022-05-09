package com.skylight.android.volunteering.app.dataSource

import com.skylight.android.volunteering.app.api.ApiService
import com.skylight.android.volunteering.app.api.GoogleService
import com.skylight.android.volunteering.app.model.FleetLocationResponse
import com.skylight.android.volunteering.app.util.MConstants
import com.skylight.android.volunteering.core.ui.ViewState
import com.skylight.android.volunteering.app.dataSource.DataRepository.DefaultDataRepository
import com.skylight.android.volunteering.app.util.NetworkError

import dagger.Binds
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 * Here persisting is not implemented
 */
interface DataRepository {
    fun getGooglePing(): Flow<String>
    fun invokeFleetLocationApi(
        p1Lat: String,
        p1Lon: String,
        p2Lat: String,
        p2Lon: String
    ): Flow<ViewState<FleetLocationResponse>>

    @Singleton
    class DefaultDataRepository @Inject constructor(
        private val apiService: ApiService,
        private val googleService: GoogleService
    ) : DataRepository {
        val TAG = "DefaultDataRepository"
        override fun getGooglePing(): Flow<String> = flow {
            val response = googleService.getGoogle()
            if (response != null && response.isSuccessful)
                emit(MConstants.SUCCESS)
            else
                emit(MConstants.TRY_AGAIN)
        }.flowOn(Dispatchers.IO).catch { exception ->
            emit(NetworkError(exception).getAppErrorMessage())
        }

        override fun invokeFleetLocationApi(
            p1Lat: String,
            p1Lon: String,
            p2Lat: String,
            p2Lon: String
        ): Flow<ViewState<FleetLocationResponse>> =
            flow {
                emit(ViewState.loading<FleetLocationResponse>(true))
                val response = apiService.getFleetLocation(
                    MConstants.ApiEndPoints.FLEET_LOCATIONS,
                    p1Lat = p1Lat,
                    p1Lon = p1Lon,
                    p2Lat = p2Lat,
                    p2Lon = p2Lon
                )
                emit(ViewState.loading<FleetLocationResponse>(false))
                emit(ViewState.success<FleetLocationResponse>(response))
            }.catch { error ->
                emit(ViewState.loading(false))
                getErrorMsg(error).collect { errorMsg ->
                    emit(ViewState.error(errorMsg))
                }
            }.flowOn(Dispatchers.IO)

        private fun getErrorMsg(error: Throwable): Flow<String> = flow {
            getGooglePing().collect { pingResponse ->
                if (pingResponse.contentEquals(MConstants.SUCCESS)) {
                    emit(NetworkError(error).getAppErrorMessage())
                } else {
                    emit(pingResponse)
                }
            }
        }.catch { exception ->
            emit(NetworkError(exception).getAppErrorMessage())
        }
    }
}

@Module
interface DataRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds
    fun it(it: DefaultDataRepository): DataRepository
}