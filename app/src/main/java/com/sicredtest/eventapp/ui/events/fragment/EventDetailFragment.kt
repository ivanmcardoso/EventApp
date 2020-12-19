package com.sicredtest.eventapp.ui.events.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.viewmodel.EventViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailFragment : Fragment() {
    private val eventViewModel by viewModel<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventViewModel.selectedEventLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Picasso.get().load(it.image).into(fed_iv_profile_image)
                fed_tv_title.text = it.title
                fed_tv_price.text = it.price.toString()
                fed_tv_date.text = it.date.toString()
                fed_tv_description.text = it.description
            }
        })
    }

}