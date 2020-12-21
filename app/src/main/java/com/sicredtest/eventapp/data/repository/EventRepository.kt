package com.sicredtest.eventapp.data.repository

import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.remoteDataSource.EventRemoteDataSource

class EventRepository(private val EventRemoteDataSource: EventRemoteDataSource) {

    var selectedEvent: Event? = null

    suspend fun getEvents(): List<Event>? {
        return try {
            var result = EventRemoteDataSource.getEvents()
            result
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun eventCheckIn(checkIn: CheckIn): Boolean {
        return try {
            val result = EventRemoteDataSource.eventCheckIn(checkIn)
            return (result != null)
        } catch (ex: Exception) {
            false
        }
    }
}