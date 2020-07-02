package com.example.retrofitexample

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("getotp")
    fun sendOtp(
        @Query("user_name") username: String,
        @Query("secret_key") secret_key: String,
        @Query("phone") phone: String
    ): Call<OtpFormat>


}
