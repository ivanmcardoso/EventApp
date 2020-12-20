package com.sicredtest.eventapp.data.remoteDataSource

import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.service.EventService

class EventRemoteDataSource(private val eventService: EventService) {
    suspend fun getEvents(): List<Event>? = eventService.getEvents().body()

    suspend fun getEventDetail(id: Int): Event? =  eventService.getEventDetail(id).body()

    suspend fun eventCheckIn(checkIn: CheckIn) = eventService.checkIn(checkIn).body()
}