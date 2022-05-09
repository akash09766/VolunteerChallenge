package com.skylight.android.volunteering.app.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * Created by Akash Wangalwar.(Github:akash09766) on 07-04-2022 at 17:21.
 */

class ItemOffsetDecoration(itemOffset: Int) : ItemDecoration() {
    private val mItemOffset: Int = itemOffset

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(
        context.resources.getDimensionPixelSize(itemOffsetId)
    ) {
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent!!, state!!)
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
    }

}