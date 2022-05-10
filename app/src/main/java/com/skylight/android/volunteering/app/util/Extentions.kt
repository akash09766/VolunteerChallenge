package com.skylight.android.volunteering.app.util

import android.provider.MediaStore
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 01-03-2022 at 17:02.
 */
/**
 * function to calculate that evening/night started or not for google map to show in night mode
 * here we are activating night more on google map after 17:45:00
 */
fun GoogleMap.isNightTime(): Boolean {
    val systemTime = Calendar.getInstance(TimeZone.getDefault()).time

    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.set(Calendar.HOUR_OF_DAY, 17)
    calendar.set(Calendar.MINUTE, 45)
    calendar.set(Calendar.SECOND, 0)

    val currentTime = calendar.time
    return systemTime > currentTime
}

fun Calendar.formatDate() : String = SimpleDateFormat(MConstants.DATE_DISPLAY_FORMAT).format(this.time)

//val Firebase.QuerySnapshot: Task<QuerySnapshot>
//    get() = FirebaseFirestore.getInstance().collection(MConstants.DataHolderKeys.Events.name).whereEqualTo(MediaStore.MediaColumns.DOCUMENT_ID, "1111").get()
