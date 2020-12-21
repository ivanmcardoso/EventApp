package com.sicredtest.eventapp.view.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.databinding.SingleListItemEventBinding
import com.sicredtest.eventapp.utils.TimeMapper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event_detail.*
import java.text.DecimalFormat

class EventAdapter(
    private val eventList: MutableList<Event>,
    private val onItemClick: (Event) -> Unit
) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: SingleListItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(event: Event) {
            Picasso.get().load(event.image).error(R.drawable.img_event_unavailable)
                .placeholder(R.drawable.img_event_unavailable).into(binding.ivEventListItemImage)
            binding.tvEventListItemTitle.text = event.title
            binding.tvEventListItemDate.text = event.date?.let { date -> TimeMapper.getDateFromTimestamp(date) }
            binding.tvEventListItemPrice.text =  "R$" + DecimalFormat("#,###.00").format(event.price)
            binding.root.setOnClickListener { onItemClick(event) }
        }
    }


    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bindView(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding: SingleListItemEventBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.single_list_item_event, parent, false)
        return EventViewHolder(binding)
    }

    fun clear() {
        eventList.clear()
        notifyDataSetChanged()
    }

    fun addAll(events: List<Event>){
        eventList.addAll(events)
        notifyDataSetChanged()
    }
}