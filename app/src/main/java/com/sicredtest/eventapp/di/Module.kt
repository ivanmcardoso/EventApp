package com.sicredtest.eventapp.di

import com.sicredtest.eventapp.data.remoteDataSource.EventRemoteDataSource
import com.sicredtest.eventapp.data.repository.EventRepository
import com.sicredtest.eventapp.data.service.ApiConfig
import com.sicredtest.eventapp.view.event.viewmodel.EventViewModel
import org.koin.dsl.module

val eventRemoteDataSource = module {
    single { EventRemoteDataSource(ApiConfig.eventService) }
}

val eventRepositoryModule = module {
    single { EventRepository(get()) }
}

val eventViewModel = module {
    single {
        EventViewModel(get())
    }
}

fun getAppModules() = listOf(eventRemoteDataSource, eventRepositoryModule, eventViewModel)