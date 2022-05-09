package com.skylight.android.volunteering.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Custom Interceptor for Retrofit to detect internet connection is available or not
 */

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    private val TAG = "NetworkConnectionInterc"

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException(MConstants.NOT_CONNECTED_TO_INTERNET)
            // Throwing our custom exception 'NoConnectivityException'
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        return getConnectionType() != ConnectionType.NONE
    }

    @SuppressLint("MissingPermission")
    private fun getConnectionType(): ConnectionType {
        var result : ConnectionType = ConnectionType.NONE // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = ConnectionType.WIFI
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = ConnectionType.MOBILE
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = ConnectionType.WIFI
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = ConnectionType.MOBILE
                    }
                }
            }
        }
        return result
    }
}
