package com.skylight.android.volunteering.app.util

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 07-04-2022 at 04:03.
 */


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.skylight.android.volunteering.app.model.event.AdminInfo
import com.skylight.android.volunteering.app.model.event.VolunteerItem

class Prefs(context: Context) {
    private val PREFS_FILENAME = context.packageName;
    private val LOGGED_IN_USER_DATA = "logged_in_user_data"
    private val IS_LOGGED_IN_USER_ADMIN = "is_logged_in_user_admin"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun isUserLoggedIn() = (prefs.getString(LOGGED_IN_USER_DATA, "").isNullOrEmpty()).not()

    fun getAdminUserData(): AdminInfo? {
        val data = prefs.getString(LOGGED_IN_USER_DATA, "")
        return if (data.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(data, AdminInfo::class.java)
        }
    }

    fun saveAdminUserData(value: AdminInfo?) {
        prefs.edit().apply {
            putString(LOGGED_IN_USER_DATA, Gson().toJson(value))
            putBoolean(IS_LOGGED_IN_USER_ADMIN, true)
        }.apply()
    }


    fun getVolunteerUserData(): VolunteerItem? {
        val data = prefs.getString(LOGGED_IN_USER_DATA, "")
        return if (data.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(data, VolunteerItem::class.java)
        }
    }

    fun saveVolunteerUserData(value: VolunteerItem?) {
        prefs.edit().apply {
            putString(LOGGED_IN_USER_DATA, Gson().toJson(value))
            putBoolean(IS_LOGGED_IN_USER_ADMIN, false)
        }.apply()
    }

    fun isLoggedInUserAdmin(): Boolean = prefs.getBoolean(IS_LOGGED_IN_USER_ADMIN, false)

    fun clearPrefs() {
        prefs.edit().clear().apply()
    }
}