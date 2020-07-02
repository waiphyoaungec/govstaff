package com.example.civilservantapp.otp_pojo

import com.google.gson.annotations.SerializedName

class OtpConfirm(
    @field:SerializedName("error_code")
    val error_code: String,
    @field:SerializedName("errorDesc")
    val errorDesc: String
)
