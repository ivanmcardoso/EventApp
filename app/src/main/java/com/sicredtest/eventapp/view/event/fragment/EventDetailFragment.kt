package com.sicredtest.eventapp.view.event.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.data.model.Event
import com.sicredtest.eventapp.utils.TimeMapper
import com.sicredtest.eventapp.view.event.dialog.CheckInDialog
import com.sicredtest.eventapp.view.event.viewmodel.EventViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

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
        setupObservers()
        setupButtons()
    }

    private fun setupButtons() {
        fed_fab_go_back.setOnClickListener { goBack() }
        fed_btn_check_in.setOnClickListener { openCheckInDialog() }
        fed_fab_share.setOnClickListener { shareEvent() }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun setupObservers() {
        eventViewModel.selectedEventLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                bindView(it)
            }
        })
    }

    private fun shareEvent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val text = eventViewModel.getShareText()
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun openCheckInDialog() {
        CheckInDialog().show(
            childFragmentManager,
            "CHECK_IN_DIALOG"
        )
    }

    private fun bindView(it: Event) {
        Picasso.get().load(it.image).error(R.drawable.img_event_unavailable)
            .placeholder(R.drawable.img_event_unavailable).into(fed_iv_profile_image)
        fed_tv_title.text = it.title
        fed_tv_price.text = "R$" + DecimalFormat("#,###.00").format(it.price)
        fed_tv_date.text = it.date?.let { date -> TimeMapper.getDateFromTimestamp(date) }
        fed_tv_description.text = it.description
    }

}