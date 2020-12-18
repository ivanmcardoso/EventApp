package com.sicredtest.eventapp.retrofit

import com.sicredtest.eventapp.model.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {
    @GET("events")
    suspend fun getEvents(): List<Event>

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") eventId: Int): Event
}