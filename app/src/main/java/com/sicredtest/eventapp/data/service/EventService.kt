package com.sicredtest.eventapp.data.service

import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {
    @GET("events")
    suspend fun getEvents(): Response<List<Event>>

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") eventId: Int): Response<Event>

    @POST("/checkin")
    suspend fun checkIn(@Body checkIn: CheckIn): Response<Any>
}