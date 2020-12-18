package com.sicredtest.eventapp.model

data class Event (
    val people: List<Any?>? = null,
    val date: Long? = null,
    val description: String? = null,
    val image: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val price: Double? = null,
    val title: String? = null,
    val id: String? = null
)
