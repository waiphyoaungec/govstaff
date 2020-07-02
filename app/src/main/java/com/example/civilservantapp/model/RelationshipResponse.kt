package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class RelationshipResponse(
    @SerializedName("description")
    var description: String?=""
)