package com.sicredtest.eventapp.di

import com.sicredtest.eventapp.repository.EventRepository
import com.sicredtest.eventapp.retrofit.RetrofitConfig
import com.sicredtest.eventapp.viewmodel.EventViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventRepositoryModule = module {
    single { EventRepository(RetrofitConfig.eventService) }
}

val eventViewModel = module {
    viewModel {
         EventViewModel(get())
    }
}