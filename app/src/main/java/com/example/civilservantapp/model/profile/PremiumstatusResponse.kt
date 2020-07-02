package com.example.civilservantapp.model.profile

import com.google.gson.annotations.SerializedName

data class PremiumstatusResponse (
    @SerializedName("message")
    var message: String?,
    @SerializedName("insurance_person_info")
    var insurance_person_info: ArrayList<InsurancePersonInfo>?,
    @SerializedName("payment_info")
    var payment_info: PaymentInfo?
)

data class InsurancePersonInfo(
    @SerializedName("army_status")
    var army : String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("policy_id")
    var policy_id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("nrc")
    var nrc: String,
    @SerializedName("father_name")
    var father_name: String,
    @SerializedName("occupation")
    var occupation: String,
    @SerializedName("permanent_address")
    var permanent_address: String,
    @SerializedName("phone_no")
    var phone_no: String,
    @SerializedName("date_of_birth")
    var date_of_birth: String,
    @SerializedName("birth_place")
    var birth_place: String,
    @SerializedName("age_next_yea")
    var age_next_yea: Int,
    @SerializedName("salary")
    var salary: Int,
    @SerializedName("premium_kyat")
    var premium_kyat: Double,
    @SerializedName("insurance_amount")
    var insurance_amount: Int,
    @SerializedName("insurance_peroid")
    var insurance_peroid: Int,
    @SerializedName("premium_status")
    var premium_status: String?=null,
    @SerializedName("previous_premium_status")
    var previous_premium_status: Int,
    @SerializedName("previous_policy_id")
    var previous_policy_id: String?=null,
    @SerializedName("previous_acceptance_status")
    var previous_acceptance_status: Int,
    @SerializedName("medicial_status")
    var medicial_status: String?=null,
    @SerializedName("drug_status")
    var drug_status: Int,
    @SerializedName("mark")
    var mark: String,
    @SerializedName("height")
    var height: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("print_status")
    var print_status: Int,
    @SerializedName("url")
    var url: String?=null,
    @SerializedName("health_recommended_letter")
    var health_recommended_letter: String?=null,
    @SerializedName("health_recommended_verify")
    var health_recommended_verify: Int,
    @SerializedName("upload_status")
    var upload_status: Int,
    @SerializedName("download_status")
    var download_status: Int,
    @SerializedName("del_status")
    var del_status: Int,
    @SerializedName("start_date")
    var start_date: String?=null,
    @SerializedName("end_date")
    var end_date: String?=null,
    @SerializedName("user_id")
    var user_id: Int,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String,
    @SerializedName("mm_township_name")
    var mm_township_name: String,
    @SerializedName("mm_district_name")
    var mm_district_name: String,
    @SerializedName("mm_state_region_name")
    var mm_state_region_name: String,
    @SerializedName("mm_dept_name")
    var mm_dept_name: String,
    @SerializedName("mm_ministry_name")
    var mm_ministry_name: String,
    @SerializedName("townships_id")
    var townships_id: Int,
    @SerializedName("district_id")
    var district_id: Int,
    @SerializedName("state_regions_id")
    var state_regions_id: Int,
    @SerializedName("department_id")
    var department_id: Int,
    @SerializedName("ministry_id")
    var ministry_id: Int
)

data class PaymentInfo(
    @SerializedName("insurance_person_id")
    var insurance_person_id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("nrc")
    var nrc: String?,
    @SerializedName("insurance_amount")
    var insurance_amount: Int?,
    @SerializedName("insurance_peroid")
    var insurance_peroid: Int?,
    @SerializedName("count_paid_month")
    var count_paid_month: Int?,
    @SerializedName("paid_month")
    var paid_month: ArrayList<String>?,
    @SerializedName("count_overdue_m")
    var count_overdue_m: Int?,
    @SerializedName("overdue_month")
    var overdue_month: ArrayList<String>?,
    @SerializedName("monthly_date")
    var monthly_date: String?,
    @SerializedName("due_date")
    var due_date: String?,
    @SerializedName("bank")
    var bank: Int?,
    @SerializedName("easypay")
    var easypay: Int?,
    @SerializedName("sh")
    var sh: Int?,
    @SerializedName("monthly_amount")
    var monthly_amount: Int?,
    @SerializedName("total_deposit_amt")
    var total_deposit_amt: Int?,
    @SerializedName("msg")
    var msg: String?,
    @SerializedName("response")
    var response: String?


)