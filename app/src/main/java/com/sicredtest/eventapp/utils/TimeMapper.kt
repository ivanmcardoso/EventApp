package com.sicredtest.eventapp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeMapper {
    fun getDateFromTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }
}