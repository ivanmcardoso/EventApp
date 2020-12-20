package com.sicredtest.eventapp.view.event.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.repository.EventRepository
import com.sicredtest.eventapp.utils.isEmailValid
import com.sicredtest.eventapp.utils.isValid
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val eventsLiveData = MutableLiveData<List<Event>?>()
    val selectedEventLiveData = MutableLiveData<Event?>()
    val checkInEventLiveData = MutableLiveData(CheckIn())
    val isEmailValid = MutableLiveData(false)
    val isNameValid = MutableLiveData(false)
    val isEventsLoading = MutableLiveData(false)
    val isCheckInLoading = MutableLiveData(false)

    val isButtonEnable = MediatorLiveData<Boolean>().apply {
        addSource(isEmailValid) {
            value = it && isNameValid.value!!
        }
        addSource(isNameValid) {
            value = it && isEmailValid.value!!
        }
    }


    fun setEmail(email: CharSequence?) {
        if (email?.isEmailValid()!!) {
            checkInEventLiveData.value?.email = email.toString()
            isEmailValid.value = true
        } else {
            isEmailValid.value = false
        }
    }

    fun setName(name: CharSequence?) {
        if (name?.isValid()!!) {
            checkInEventLiveData.value?.name = name.toString()
            isNameValid.value = true
        } else {
            isNameValid.value = false
        }
    }

    fun setSelectedEvent(event: Event) {
        selectedEventLiveData.value = event
        checkInEventLiveData.value?.eventId = event.id
    }

    fun fetchEvents(): MutableLiveData<List<Event>?> {
        isEventsLoading.value = true
        viewModelScope.launch {
            val events = eventRepository.getEvents()
            eventsLiveData.value = events
            isEventsLoading.value = false
        }
        return eventsLiveData
    }

    fun checkIn() {
        isCheckInLoading.value = true
        val checkIn = checkInEventLiveData.value
        viewModelScope.launch {
            checkIn?.let {
                eventRepository.eventCheckIn(it)
            }
            isCheckInLoading.value = false
        }
    }

    fun clearCheckIn() {
        checkInEventLiveData.value = CheckIn(eventId = selectedEventLiveData.value?.id)
        isNameValid.value = false
        isEmailValid.value = false
    }
}