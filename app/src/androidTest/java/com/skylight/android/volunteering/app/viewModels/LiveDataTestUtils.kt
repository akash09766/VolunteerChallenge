package com.skylight.android.volunteering.app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 24-02-2022 at 21:39.
 * Source google sample code from android architecture components on github
 */

fun <T> LiveData<T>.getOrAwaitValue(latchCount : Int): T {

    var data: T? = null
    val latch = CountDownLatch(latchCount)

    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            if(latch.count == 0L)
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    try {
        if (!latch.await(55, TimeUnit.SECONDS)) {
            throw RuntimeException("Unable to fetch")
        }
    } finally {
        this.removeObserver(observer)
    }
    return data as T
}