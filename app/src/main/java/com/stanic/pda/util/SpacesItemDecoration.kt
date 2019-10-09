package com.stanic.pda.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration(var bottomSpace : Int , var leftSpace : Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.bottom = bottomSpace
        outRect.left = leftSpace
        outRect.right = leftSpace
    }


}