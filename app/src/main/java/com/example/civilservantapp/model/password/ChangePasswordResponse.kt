package com.example.civilservantapp.model.password

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
   @SerializedName("message")
   var message: String?,
   @SerializedName("data")
   var data: String?
)