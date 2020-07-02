package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
 @SerializedName("response")
 var response: String,
 @SerializedName("message")
 var message: Message,
 @SerializedName("data")
 var data : String
)

data class Message(
    @SerializedName("username")
    var username: Array<String>?=null,
    @SerializedName("phone")
    var phone: Array<String>?=null,
    @SerializedName("password")
    var password: Array<String>?=null,
    @SerializedName("msg")
    var msg: String? = ""
)