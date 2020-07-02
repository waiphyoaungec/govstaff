package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class InsuranceAmtResponse(
@SerializedName("id")
var id: String?="",
@SerializedName("eng_amt")
var eng_amt: String?="",
@SerializedName("mm_amt")
var mm_amt: String?=""
)