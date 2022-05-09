package com.skylight.android.volunteering.app.util

import java.io.IOException

/**
 * Custom exception of no internet connection
 */

class NoConnectivityException(val msg: String) : IOException() {
    override val message: String?
        get() = msg
}