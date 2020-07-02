package com.example.civilservantapp.model.payment

import com.google.gson.annotations.SerializedName

data class FeeResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("bank")
    var bank: Int,
    @SerializedName("easy_pay")
    var easy_pay: Int,
    @SerializedName("sh")
    var sh: Int,
    @SerializedName("del_status")
    var del_status: Int,
    @SerializedName("created_at")
    var created_at: String?=null,
    @SerializedName("updated_at")
    var updated_at: String?=null
)