package com.example.clickerevolution.utils

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class NoAnimationItemAnimator : DefaultItemAnimator() {
    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchRemoveFinished(holder)
        return false
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(holder)
        return false
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int, fromY: Int,
        toX: Int, toY: Int
    ): Boolean {
        dispatchMoveFinished(holder)
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int, fromY: Int,
        toX: Int, toY: Int
    ): Boolean {
        dispatchChangeFinished(oldHolder, true)
        dispatchChangeFinished(newHolder, false)
        return false
    }
}