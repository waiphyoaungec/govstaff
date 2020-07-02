package com.example.civilservantapp.model.SubStractList

import com.google.gson.annotations.SerializedName

data class DepartmentResponse(
@SerializedName("departments")
var departments: ArrayList<DepartmentList>?=null
)

data class DepartmentList(
    @SerializedName("id")
    var id: Int?=0,
    @SerializedName("en_dept_name")
    var en_dept_name: String?="",
    @SerializedName("mm_dept_name")
    var mm_dept_name: String?="",
    @SerializedName("ministry_id")
    var ministry_id: Int?=0,
    @SerializedName("created_at")
    var created_at: String?="",
    @SerializedName("updated_at")
    var updated_at: String?=""
)