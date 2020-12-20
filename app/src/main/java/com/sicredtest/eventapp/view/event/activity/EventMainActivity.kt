package com.sicredtest.eventapp.view.event.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.sicredtest.eventapp.R
import com.sicredtest.eventapp.view.event.fragment.EvenListFragment

class EventMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fl_main_activity_frame, EvenListFragment()).commit()
    }
    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}