package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NrcResponse(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("mm_state_number")
    var mm_state_number: String?="",
    @SerializedName("en_short_district")
    var en_short_district: String?="",
    @SerializedName("en_state_number")
    var en_state_number: String?="",
    @SerializedName("long_district")
    var long_district: String?="",
    @SerializedName("mm_short_district")
    var mm_short_district: String?="",
    @SerializedName("created_at")
    var created_at: Date?=null,
    @SerializedName("updated_at")
    var updated_at: Date?=null
)
