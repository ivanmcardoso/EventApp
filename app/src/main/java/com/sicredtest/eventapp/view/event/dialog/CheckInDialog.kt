package com.sicredtest.eventapp.view.event.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.utils.ViewMapper
import com.sicredtest.eventapp.utils.onTextChanged
import com.sicredtest.eventapp.view.event.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.dialog_check_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInDialog() : DialogFragment() {
    private val eventViewModel by viewModel<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDialogParams()
        return inflater.inflate(R.layout.dialog_check_in, container, false)
    }

    private fun setupDialogParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showForm()
        setupListeners()
        setupObservers()

    }

    private fun showForm() {
        dci_group_formView.visibility = ViewMapper.mapBooleanToVisibility(true)
    }

    private fun setupListeners() {
        dci_tiet_name.onTextChanged { eventViewModel.setName(it) }
        dci_tiet_email.onTextChanged { eventViewModel.setEmail(it) }
        dci_tiet_email.setOnFocusChangeListener { v, hasFocus -> hideKeyboard(v, hasFocus) }
        dci_tiet_name.setOnFocusChangeListener { v, hasFocus -> hideKeyboard(v, hasFocus) }
        dci_btn_cancel.setOnClickListener { dismiss() }
        dci_btn_confirm.setOnClickListener {
            eventViewModel.checkIn()
            dci_group_formView.visibility = ViewMapper.mapBooleanToVisibility(false)
        }
    }

    private fun setupObservers() {
        eventViewModel.isButtonEnable.observe(viewLifecycleOwner, Observer {
            dci_btn_confirm.isEnabled = it
        })
        eventViewModel.isCheckInSuccess.observe(viewLifecycleOwner, Observer {
            dci_animation_view_success.visibility = ViewMapper.mapBooleanToVisibility(it)
            dci_tv_status_success.visibility = ViewMapper.mapBooleanToVisibility(it)
        })
        eventViewModel.isCheckInFailure.observe(viewLifecycleOwner, Observer {
            dci_animation_view_failure.visibility = ViewMapper.mapBooleanToVisibility(it)
            dci_tv_status_failure.visibility = ViewMapper.mapBooleanToVisibility(it)
        })
        eventViewModel.isCheckInLoading.observe(viewLifecycleOwner, Observer {
            dci_animation_view_loading.visibility = ViewMapper.mapBooleanToVisibility(it)
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        eventViewModel.clearCheckIn()
    }

    private fun hideKeyboard(view: View, hasFocus: Boolean) {
        if(!hasFocus) {
            val hideBoard =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            hideBoard?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}