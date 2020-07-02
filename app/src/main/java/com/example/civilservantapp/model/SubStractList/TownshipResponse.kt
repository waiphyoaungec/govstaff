package com.example.civilservantapp.model.SubStractList

import com.google.gson.annotations.SerializedName

data class TownshipResponse(
@SerializedName("townships")
var townships: ArrayList<TownShipList>?=null
)

data class TownShipList(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("township_code")
    var township_code: String?="",
    @SerializedName("en_township_name")
    var en_township_name: String?="",
    @SerializedName("mm_township_name")
    var mm_township_name: String?="",
    @SerializedName("district_id")
    var district_id: Int?=0,
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)