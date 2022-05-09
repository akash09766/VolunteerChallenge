package com.skylight.android.volunteering.core.utils

import kotlin.collections.HashMap

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 03-04-2022 at 00:40.
 */
class DataCache {

    private val dataHolder = HashMap<String, Object>()

    public fun get(key: String) = dataHolder[key]
    public fun put(key: String, value: Object) {
        dataHolder[key] = value
    }

    public fun clearCache(){
        dataHolder.clear()
    }

}