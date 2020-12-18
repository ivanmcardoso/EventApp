package com.sicredtest.eventapp.ui.events.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.databinding.EventListItemBinding
import com.sicredtest.eventapp.model.Event
import com.squareup.picasso.Picasso

class EventAdapter(
    private val eventList: List<Event>,
    private val onItemClick: (Event) -> Unit
) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(event: Event) {
            Picasso.get().load(event.image).into(binding.ivEventListItemImage)
            binding.tvEventListItemTitle.setText(event.title)
            binding.tvEventListItemDate.text = event.date.toString()
            binding.tvEventListItemPrice.text = event.price.toString()
            binding.root.setOnClickListener { onItemClick(event) }
        }
    }


    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bindView(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding: EventListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.event_list_item, parent, false)
        return EventViewHolder(binding)
    }
}