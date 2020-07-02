package com.example.civilservantapp.model.SubStractList

import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("districts")
    var districts: ArrayList<DistrictList>?=null
)

data class DistrictList(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("district_code")
    var district_code: String?="",
    @SerializedName("en_district_name")
    var en_district_name: String?="",
    @SerializedName("mm_district_name")
    var mm_district_name: String?="",
    @SerializedName("state_region_id")
    var state_region_id: Int?=0,
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)