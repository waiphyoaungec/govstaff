package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class PremiumResponse (
    @SerializedName("deposit_amount")
    var deposit_amount: Int?=0
)