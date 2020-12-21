package com.sicredtest.eventapp.utils

import android.view.View

object ViewMapper {
    fun mapBooleanToVisibility(boolean: Boolean) = if (boolean) View.VISIBLE else View.GONE
}