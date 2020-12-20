package com.sicredtest.eventapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    val people: List<CheckIn>? = null,
    val date: Long? = null,
    val description: String? = null,
    val image: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val price: Double? = null,
    val title: String? = null,
    val id: String? = null
): Parcelable
