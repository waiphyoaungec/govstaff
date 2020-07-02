package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class NrcListResponse(
    @SerializedName("mm_short_district")
    var mm_short_district: String?="",
    @SerializedName("long_district")
    var long_district: String?=""
)