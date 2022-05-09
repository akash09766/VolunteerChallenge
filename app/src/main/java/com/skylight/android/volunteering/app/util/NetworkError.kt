package com.skylight.android.volunteering.app.util

import com.google.gson.JsonParseException
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import retrofit2.HttpException
import java.net.*
import javax.net.ssl.SSLHandshakeException

/**
 * Static class to get user error based on type of exception
 */

class NetworkError(private val throwable: Throwable) : Throwable() {

    fun getAppErrorMessage(): String {
        when (throwable) {
            is UnknownHostException -> {
                return MConstants.SERVER_NOT_RESPONDING
            }
            is NoConnectivityException -> {
                return MConstants.NOT_CONNECTED_TO_INTERNET
            }
            is ConnectException -> {
                return MConstants.NO_INTERNET
            }
            is SocketTimeoutException -> {
                return MConstants.SERVER_NOT_RESPONDING
            }
            is ConnectionShutdownException -> {
                return MConstants.NO_INTERNET
            }
            is SSLHandshakeException -> {
                return MConstants.TRY_AGAIN
            }
            is StreamResetException -> {
                return MConstants.TRY_AGAIN
            }
            is ProtocolException -> {
                return MConstants.TRY_AGAIN
            }
            is UnknownServiceException -> {
                return MConstants.TRY_AGAIN
            }
            is IllegalStateException -> {
                return MConstants.INVALID_SERVER_RESPONSE
            }
            is JsonParseException -> {
                return MConstants.INVALID_SERVER_RESPONSE
            }
            is HttpException -> {
                return MConstants.INVALID_SERVER_RESPONSE
            }
            else -> {
                return MConstants.TRY_AGAIN
            }
        }
    }
}