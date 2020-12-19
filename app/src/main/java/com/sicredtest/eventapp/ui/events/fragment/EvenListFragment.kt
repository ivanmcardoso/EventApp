package com.sicredtest.eventapp.ui.events.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.model.Event
import com.sicredtest.eventapp.ui.events.adapter.EventAdapter
import com.sicredtest.eventapp.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.fragment_even_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EvenListFragment : Fragment() {

    private val eventViewModel by viewModel<EventViewModel>()
    private val eventList = mutableListOf<Event>()
    private val eventAdapter = EventAdapter(eventList) {
        Toast.makeText(requireContext(), it.title, Toast.LENGTH_LONG).show()
        eventViewModel.selectedEventLiveData.value = it
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_main_activity_frame, EventDetailFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_even_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventList()
        setUpEventListWithRecyclerView()
    }

    private fun setUpEventListWithRecyclerView() {
        rv_event_list.adapter = eventAdapter
    }

    private fun setupEventList() {
        eventViewModel.fetchEvents().observe(viewLifecycleOwner, Observer {
            it?.let {
                updateEventRecycleView(it)
            }
        })
    }

    private fun updateEventRecycleView(updatedEventList: List<Event>) {
        eventList.clear()
        eventList.addAll(updatedEventList)
        eventAdapter.notifyDataSetChanged()
    }

}