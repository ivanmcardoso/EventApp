package com.sicredtest.eventapp

import android.app.Application
import com.sicredtest.eventapp.di.eventRepositoryModule
import com.sicredtest.eventapp.di.eventViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(listOf(eventRepositoryModule, eventViewModel))
        }
    }
}