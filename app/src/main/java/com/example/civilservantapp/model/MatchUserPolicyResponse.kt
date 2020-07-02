package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class MatchUserPolicyResponse(
    @SerializedName("message")
    var message: String?="",
    @SerializedName("user_id")
    var user_id: Int?=0,
    @SerializedName("policy_id")
    var policy_id: String?=""
)