package com.example.civilservantapp.model.profile

import com.google.gson.annotations.SerializedName

data class JobHistoryResponse(
@SerializedName("current_page")
var current_page: Int,
@SerializedName("data")
var data: ArrayList<HistoryData>?,
@SerializedName("first_page_url")
var first_page_url: String,
@SerializedName("from")
var from: Int,
@SerializedName("last_page")
var last_page: Int,
@SerializedName("last_page_url")
var last_page_url: String,
@SerializedName("next_page_url")
var next_page_url: String,
@SerializedName("path")
var path: String,
@SerializedName("per_page")
var per_page: Int,
@SerializedName("prev_page_url")
var prev_page_url: String?=null,
@SerializedName("to")
var to: Int,
@SerializedName("total")
var total: Int
)

data class HistoryData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("insurance_person_id")
    var insurance_person_id: Int,
    @SerializedName("occupation")
    var occupation: String,
    @SerializedName("township_id")
    var township_id: Int,
    @SerializedName("dept_id")
    var dept_id: Int,
    @SerializedName("del_status")
    var del_status: Int,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("policy_id")
    var policy_id: String,
    @SerializedName("mm_dept_name")
    var mm_dept_name: String,
    @SerializedName("mm_ministry_name")
    var mm_ministry_name: String,
    @SerializedName("mm_state_region_name")
    var mm_state_region_name: String,
    @SerializedName("mm_district_name")
    var mm_district_name: String,
    @SerializedName("mm_township_name")
    var mm_township_name: String
)