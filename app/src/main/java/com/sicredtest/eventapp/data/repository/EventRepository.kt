package com.sicredtest.eventapp.data.repository

import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.remoteDataSource.EventRemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Response

class EventRepository(private val EventRemoteDataSource: EventRemoteDataSource) {

    suspend fun getEvents(): List<Event>? {
        return try {
            var result = EventRemoteDataSource.getEvents()
            result
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getEvent(id: Int): Event? {
        return try {
            var result = EventRemoteDataSource.getEventDetail(id)
            result
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun eventCheckIn(checkIn: CheckIn): Any? {
        return try {
            var result = EventRemoteDataSource.eventCheckIn(checkIn)
            result
        } catch (ex: Exception) {
            null
        }
    }
}