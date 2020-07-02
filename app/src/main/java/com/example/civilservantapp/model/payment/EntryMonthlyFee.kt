package com.example.civilservantapp.model.payment

import com.google.gson.annotations.SerializedName

data class EntryMonthlyFee(
    @SerializedName("message")
    var message: Boolean?=null,
    @SerializedName("insurance_person_id")
    var insurance_person_id: String?=null,
    @SerializedName("invoice_id")
    var invoice_id: String?=null,
    @SerializedName("amount")
    var amount: Int?=null,
    @SerializedName("response")
    var response: String?=null,
    @SerializedName("msg")
    var messageList: MessageList?=null
)

data class MessageList(
    @SerializedName("amount")
    var amount: ArrayList<String>?=null
)