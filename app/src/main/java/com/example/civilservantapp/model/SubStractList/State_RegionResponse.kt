package com.example.civilservantapp.model.SubStractList

import com.google.gson.annotations.SerializedName

data class State_RegionResponse(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("en_state_region_name")
    var en_state_region_name: String?="",
    @SerializedName("mm_state_region_name")
    var mm_state_region_name: String?="",
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)