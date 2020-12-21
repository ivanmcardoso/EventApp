package com.sicredtest.eventapp.view.event.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.repository.EventRepository
import com.sicredtest.eventapp.utils.MockitoHelper
import com.sicredtest.eventapp.utils.isEmailValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class EventViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var eventViewModel: EventViewModel
    private lateinit var eventRepository: EventRepository

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        eventRepository = mock(EventRepository::class.java)
        eventViewModel = EventViewModel(eventRepository)
    }

    @Test
    fun fetchEvents_success() {
        val events: List<Event> = listOf(mock(Event::class.java), mock(Event::class.java))
        `when`(runBlocking { eventRepository.getEvents() }).thenReturn(events)
        val observer = mock(Observer::class.java) as Observer<in List<Event>?>
        eventViewModel.fetchEvents().observeForever(observer)
        assertNotNull(eventViewModel.eventsLiveData.value)
        assertEquals(eventViewModel.eventsLiveData.value, events)
    }
    @Test
    fun fetchEvents_failure() {
        `when`(runBlocking { eventRepository.getEvents() }).thenReturn(null)
        val observer = mock(Observer::class.java) as Observer<in List<Event>?>
        eventViewModel.fetchEvents().observeForever(observer)
        assertEquals(eventViewModel.eventsLiveData.value, null)
    }

    @Test
    fun checkIn_success() {
        Mockito.`when`(runBlocking { eventRepository.eventCheckIn(MockitoHelper.anyObject()) }).thenReturn(true)
        val observer = mock(Observer::class.java) as Observer<in Boolean?>
        eventViewModel.isCheckInSuccess.observeForever(observer)
        eventViewModel.isCheckInFailure.observeForever(observer)
        eventViewModel.checkIn()
        assertEquals(eventViewModel.isCheckInSuccess.value, true)
        assertEquals(eventViewModel.isCheckInFailure.value, false)
    }

    @Test
    fun checkIn_failure() {
        Mockito.`when`(runBlocking { eventRepository.eventCheckIn(MockitoHelper.anyObject()) }).thenReturn(false)
        val observer = mock(Observer::class.java) as Observer<in Boolean?>
        eventViewModel.isCheckInSuccess.observeForever(observer)
        eventViewModel.isCheckInFailure.observeForever(observer)
        eventViewModel.checkIn()
        assertEquals(eventViewModel.isCheckInSuccess.value, false)
        assertEquals(eventViewModel.isCheckInFailure.value, true)
    }

    @Test
    fun validate_Clear_Checkin() {
        val observer = mock(Observer::class.java) as Observer<in Boolean?>
        eventViewModel.isCheckInSuccess.observeForever(observer)
        eventViewModel.isCheckInFailure.observeForever(observer)
        eventViewModel.isEmailValid.observeForever(observer)
        eventViewModel.isNameValid.observeForever(observer)
        eventViewModel.clearCheckIn()
        assertEquals(eventViewModel.isCheckInSuccess.value, false)
        assertEquals(eventViewModel.isNameValid.value, false)
        assertEquals(eventViewModel.isEmailValid.value, false)
        assertEquals(eventViewModel.isCheckInFailure.value, false)
    }

    @Test
    fun validate_set_selected_event() {
        val selectedEvent = mock(Event::class.java)
        val eventObserver = mock(Observer::class.java) as Observer<in Event?>
        val checkInObserver = mock(Observer::class.java) as Observer<in CheckIn?>
        eventViewModel.selectedEventLiveData.observeForever(eventObserver)
        eventViewModel.checkInEventLiveData.observeForever(checkInObserver)
        eventViewModel.setSelectedEvent(selectedEvent)
        assertNotNull(eventViewModel.selectedEventLiveData.value)
        assertEquals(eventViewModel.selectedEventLiveData.value, selectedEvent)
        assertNotNull(eventViewModel.checkInEventLiveData.value)
        assertEquals(eventViewModel.checkInEventLiveData.value?.eventId, selectedEvent.id)
    }

}