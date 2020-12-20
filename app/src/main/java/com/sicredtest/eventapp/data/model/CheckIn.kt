package com.sicredtest.eventapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckIn(
    var eventId: String? = null,
    var name: String = "",
    var email: String = ""
) : Parcelable