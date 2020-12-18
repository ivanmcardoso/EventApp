package com.sicredtest.eventapp.repository

import com.sicredtest.eventapp.model.Event
import com.sicredtest.eventapp.retrofit.EventService
import com.sicredtest.eventapp.retrofit.RetrofitConfig
import retrofit2.Response

class EventRepository(private val eventService: EventService) {

    suspend fun getEvents(): List<Event>? {
        return try {
            var result = eventService.getEvents()
            result
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getEvent(id: Int): Event? {
        return try {
            var result = eventService.getEventDetail(1)
            result
        } catch (ex: Exception) {
            null
        }
    }
}