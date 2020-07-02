package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class OfficeResponse(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("office_name")
    var office_name: String?="",
    @SerializedName("bank_code")
    var bank_code: String?="",
    @SerializedName("policy_prefix")
    var policy_prefix: String?="",
    @SerializedName("del_status")
    var del_status: Int?=0,
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)