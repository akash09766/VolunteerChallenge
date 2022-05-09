package com.skylight.android.volunteering.app.listerners

import com.skylight.android.volunteering.app.model.PoiListItem

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 23-02-2022 at 02:27.
 * Generic Item click Listener for Recyclerview.
 */
interface ListItemClickListener<T> {
    fun onItemClick(data : T)
}