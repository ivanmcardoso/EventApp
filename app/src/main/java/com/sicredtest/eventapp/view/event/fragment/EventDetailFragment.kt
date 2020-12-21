package com.sicredtest.eventapp.view.event.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.R
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
        eventViewModel.selectedEventLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Picasso.get().load(it.image).error(R.drawable.img_event_unavailable)
                    .placeholder(R.drawable.img_event_unavailable).into(fed_iv_profile_image)
                fed_tv_title.text = it.title
                fed_tv_price.text = "R$" + DecimalFormat("#,###.00").format(it.price)
                fed_tv_date.text = it.date?.let { date -> TimeMapper.getDateFromTimestamp(date) }
                fed_tv_description.text = it.description
            }
        })
        fed_fab_go_back.setOnClickListener { parentFragmentManager.popBackStack() }
        fed_btn_check_in.setOnClickListener {
            CheckInDialog().show(
                childFragmentManager ,
                "CHECK_IN_DIALOG"
            )
        }
        fed_fab_share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

}