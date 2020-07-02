package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Disease(
    @SerializedName("id")
    var id: Int,
    @SerializedName("description")
    var description: String?=""
)