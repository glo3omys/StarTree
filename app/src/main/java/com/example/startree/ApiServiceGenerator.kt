package com.example.startree

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceGenerator {
    private val URL = "http://116.122.180.16:8000" // fast api

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}