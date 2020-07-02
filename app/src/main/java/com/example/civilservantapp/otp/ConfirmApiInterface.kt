package com.example.civilservantapp.otp


import com.example.civilservantapp.otp_pojo.OtpConfirm
import com.example.retrofitexample.OtpFormat
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface ConfirmApiInterface {

    @POST("verifyotp")
    fun confirm(
        @Query("otp") otp: String,
        @Query("phone") phone: String
    ): Call<OtpConfirm>


}
