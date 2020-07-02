package com.example.civilservantapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HostRetrofit {
    private var logging = HttpLoggingInterceptor()
    //set logging level
    private val okHttpClient = OkHttpClient.Builder()
    fun getRetrofit(): Retrofit {
        logging.apply { logging.level = HttpLoggingInterceptor.Level.HEADERS }
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        okHttpClient
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .networkInterceptors().add(logging)
        return Retrofit.Builder()
            .baseUrl("http://67.207.85.58/gov_insurance/gov_insurance_api/api/")
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}