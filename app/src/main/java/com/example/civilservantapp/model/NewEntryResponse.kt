package com.example.civilservantapp.model

import com.google.gson.annotations.SerializedName

data class NewEntryResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("insurance_person_id")
    var insurance_person_id: Int,
    @SerializedName("newinvoice")
    var newinvoice: NewInvoice
)

data class NewInvoice(
    @SerializedName("name")
    var name: String?="",
    @SerializedName("invoice_num")
    var invoice_num: String?="",
    @SerializedName("amount")
    var amount: Int?=0,
    @SerializedName("address")
    var address: String?="",
    @SerializedName("created_date")
    var created_date:String?="",
    @SerializedName("policy_id")
    var policy_id: String?="",
    @SerializedName("start_date")
    var start_date: String?="",
    @SerializedName("id")
    var id: Int,
    @SerializedName("monthly_date")
    var monthly_date:String?="",
    @SerializedName("due_date")
    var due_date: String?=""

)