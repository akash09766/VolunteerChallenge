package com.skylight.android.volunteering.app.util

import com.skylight.android.volunteering.BuildConfig

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 03:00.
 */

object MConstants {
    //https://fake-poi-api.mytaxi.com/?p1Lat=53.694865&p1Lon=9.757589&p2Lat=53.394655&p2Lon=10.099891
    const val BASE_URL = BuildConfig.SERVER_URL
    const val G_BASE_URL = "https://google.com"
    const val CONNECTION_TIME_OUT = 25L

    object ApiEndPoints {
        const val FLEET_LOCATIONS = ""
    }

    //platform
    const val ANDROID_PLAFORM = "android"

    // error msg for server failed or bad internet
    const val NOT_CONNECTED_TO_INTERNET = "You are not connected to internet."
    const val NO_INTERNET =
        "The internet connection you are connected to is not working please check."
    const val SERVER_NOT_RESPONDING =
        "Somethings wrong with our servers at the moment. Our best minds are on it. Please try again after some time."
    const val INVALID_SERVER_RESPONSE =
        "Somethings wrong with our servers at this movement.Our best minds are on it.\n Please try after some time."
    const val NETWORK_NOT_AVAILABLE_MSG = "Network not available."
    const val TRY_AGAIN = "Something went wrong please try again!"
    const val SNACK_BAR_OKAY_BTN_TEXT = "Okay"
    const val SNACK_BAR_TRY_AGAIN_BTN_TEXT = "Try Again"


    // server api status
    const val SUCCESS = "SUCCESS"
    const val FAILED = "FAILED"
    const val ERROR = "ERROR"

    const val SUCCESS_CODE = 200

    const val OPPS_TITLE = "Oops!"

    //some error codes
    const val WENT_WRONG_ID = 1
    const val BACKEND_ERROR__ID = 2
    const val NETWORK_ERROR__ID = 3

    val point1 = Pair("53.694865", "9.757589")
    val point2 = Pair("53.394655", "10.099891")

    enum class FleetType {
        TAXI, POOLING
    }

    enum class DataHolderKeys {
        Events, SPEAKER_DATA, ORGANISATIONS, VOLUNTEERS, ADMINS
    }

    const val ORG_ID = "Organisation_2"
//    const val event_to_be_search_and_register = "b2b11137-8adc-440b-8729-8a46faa0fad5"
//    const val volunteer_user_id = "1649145260226wangalwar.akash@gmail.com"
    const val LOGGED_IN_USER_EMAIL_ID = "akashwangalwar@dbs.com"//"wangalwar.akash@gmail.com"

    const val ADD_ORGANISATION = 0
    const val CREATE_EVENT = 1
    const val ADD_ADMIN = 2
    const val VOLUNTEER = 3
    const val EVENT = 4
    const val ORGANISATIONS = 5
}