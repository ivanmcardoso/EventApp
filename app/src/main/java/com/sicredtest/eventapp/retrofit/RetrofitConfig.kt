package com.sicredtest.eventapp.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://5f5a8f24d44d640016169133.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

     var eventService: EventService = retrofit.create(EventService::class.java)
}