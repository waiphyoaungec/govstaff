package com.example.civilservantapp.model.profile

import com.google.gson.annotations.SerializedName

data class UpdateJobHistoryResponse(
    @SerializedName("message")
    var message: String? = null
)