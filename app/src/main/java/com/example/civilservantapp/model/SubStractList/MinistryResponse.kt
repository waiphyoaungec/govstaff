package com.example.civilservantapp.model.SubStractList

import com.google.gson.annotations.SerializedName

data class MinistryResponse(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("en_ministry_name")
    var en_ministry_name: String?="",
    @SerializedName("mm_ministry_name")
    var mm_ministry_name: String?="",
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)