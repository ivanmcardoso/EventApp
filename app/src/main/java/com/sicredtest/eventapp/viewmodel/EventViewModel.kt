package com.sicredtest.eventapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sicredtest.eventapp.model.Event
import com.sicredtest.eventapp.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {

    val eventsLiveData = MutableLiveData<List<Event>?>()
    val selectedEventLiveData = MutableLiveData<Event?>()

    fun fetchEvents(): MutableLiveData<List<Event>?> {
        viewModelScope.launch {
            val events = eventRepository.getEvents()
            eventsLiveData.value = events
        }
        return eventsLiveData
    }

    fun fetchEventDetail(id: Int): MutableLiveData<Event?> {
        viewModelScope.launch {
            val event = eventRepository.getEvent(id)
            selectedEventLiveData.value = event
        }
        return selectedEventLiveData
    }
}