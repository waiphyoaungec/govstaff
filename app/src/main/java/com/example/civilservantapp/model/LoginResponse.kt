package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("response")
    var response: String?="",
    @SerializedName("message")
    var message: LoginMsg
)
data class LoginMsg(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("name")
    var name: String?="",
    @SerializedName("username")
    var username: String?="",
    @SerializedName("email")
    var email: String?="",
    @SerializedName("phone")
    var phone: String?="",
    @SerializedName("token")
    var token: String?="",
    @SerializedName("validation_fail")
    var validation_fail: String?=null,
    @SerializedName("policy_id")
    var policy_id: String?=""
)