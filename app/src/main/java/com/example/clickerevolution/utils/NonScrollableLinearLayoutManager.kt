package com.example.clickerevolution.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollableLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        // Возвращаем false, чтобы запретить вертикальную прокрутку
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        // Возвращаем false, чтобы запретить горизонтальную прокрутку
        return false
    }
}