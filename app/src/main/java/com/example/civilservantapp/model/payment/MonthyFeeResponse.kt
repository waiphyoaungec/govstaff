package com.example.civilservantapp.model.payment

import com.google.gson.annotations.SerializedName

data class MonthyFeeResponse(
    @SerializedName("insurance_person_id")
    var insurance_person_id: Int?=null,
    @SerializedName("name")
    var name: String?=null,
    @SerializedName("nrc")
    var nrc: String?=null,
    @SerializedName("age")
    var age: String?=null,
    @SerializedName("insurance_amount")
    var insurance_amount: Int?=null,
    @SerializedName("insurance_peroid")
    var insurance_peroid: String?=null,
    @SerializedName("count_paid_month")
    var count_paid_month: Int?=null,
    @SerializedName("paid_month")
    var paid_month: ArrayList<String>?=null,
    @SerializedName("count_overdue_m")
    var count_overdue_m: Int?=null,
    @SerializedName("overdue_month")
    var overdue_month: ArrayList<String>?=null,
    @SerializedName("monthly_date")
    var monthly_date: String?=null,
    @SerializedName("due_date")
    var due_date: String?=null,
    @SerializedName("bank")
    var bank: Int?=null,
    @SerializedName("easypay")
    var easypay: Int?=null,
    @SerializedName("sh")
    var sh: Int?=null,
    @SerializedName("monthly_amount")
    var monthly_amount: Int?=null,
    @SerializedName("total_deposit_amt")
    var total_deposit_amt: Int?=null,
    @SerializedName("office_id")
    var office_id: Int?=null,
    @SerializedName("msg")
    var msg: String?="",
    @SerializedName("response")
    var response: String?=""
)