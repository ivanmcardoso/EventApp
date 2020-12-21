package com.sicredtest.eventapp.view.event.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.utils.ViewMapper
import com.sicredtest.eventapp.view.event.adapter.EventAdapter
import com.sicredtest.eventapp.view.event.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EvenListFragment : Fragment() {

    private val eventViewModel by viewModel<EventViewModel>()
    private val eventList = mutableListOf<Event>()
    private val eventAdapter = EventAdapter(eventList) {
        setSelectedEvent(it)
        goToEventDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventList()
        setUpEventListWithRecyclerView()
        setupRefresh()
        setupObservers()
    }

    private fun setupRefresh() {
        fel_swipe_refresh.setOnRefreshListener { setupEventList() }
    }

    private fun setupObservers() {
        eventViewModel.eventsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateEventRecycleView(it)
                fel_group_empty_list.visibility = ViewMapper.mapBooleanToVisibility(it.isEmpty())
            } ?: run {
                showErrorMessage()
                val shouldShowEmptyList = eventAdapter.itemCount == 0
                fel_group_empty_list.visibility = ViewMapper.mapBooleanToVisibility(shouldShowEmptyList)
            }
            fel_swipe_refresh.isRefreshing = false
        })
        eventViewModel.isEventsLoading.observe(viewLifecycleOwner, Observer {
            fel_animation_view_loading.visibility = ViewMapper.mapBooleanToVisibility(it)
            fel_rv_event_list.visibility = ViewMapper.mapBooleanToVisibility(!it)
        })
    }

    private fun showErrorMessage() {
        Snackbar.make(fel_rv_event_list, "Falha ao carregar eventos", Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpEventListWithRecyclerView() {
        fel_rv_event_list.adapter = eventAdapter
    }

    private fun setupEventList() {
        eventViewModel.fetchEvents()
    }

    private fun updateEventRecycleView(updatedEventList: List<Event>) {
        eventAdapter.clear()
        eventAdapter.addAll(updatedEventList)
    }

    private fun goToEventDetailFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_main_activity_frame, EventDetailFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setSelectedEvent(it: Event) {
        eventViewModel.setSelectedEvent(it)
    }


}