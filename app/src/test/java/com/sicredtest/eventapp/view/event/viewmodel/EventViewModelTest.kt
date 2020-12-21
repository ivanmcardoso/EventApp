package com.sicredtest.eventapp.view.event.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.data.model.CheckIn
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.data.repository.EventRepository
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
import org.mockito.Mockito
import org.mockito.Mockito.mock
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
        Mockito.`when`(runBlocking { eventRepository.getEvents() }).thenReturn(events)
        val observer = mock(Observer::class.java) as Observer<in List<Event>?>
        eventViewModel.fetchEvents().observeForever(observer)
        assertNotNull(eventViewModel.eventsLiveData.value)
        assertEquals(eventViewModel.eventsLiveData.value, events)
    }

}