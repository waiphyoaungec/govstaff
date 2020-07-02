package com.example.civilservantapp.api

import com.example.civilservantapp.model.*
import com.example.civilservantapp.model.beneficiary.BeneficiaryPercent
import com.example.civilservantapp.model.joinpolicy.JoinPolicy
import com.example.civilservantapp.model.profile.UpdateJobHistoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface LifeInsuranceApi {
    @GET("c/nrc")
    fun getNrc(): Call<ArrayList<NrcResponse>>

    @GET("c/insurance_amt")
    fun getInsuranceAmount(): Call<ArrayList<InsuranceAmtResponse>>

    @GET("c/medicial")
    fun getMedicalList(): Call<ArrayList<Disease>>

    @GET("c/office")
    fun getOfficeList(): Call<ArrayList<OfficeResponse>>

    @FormUrlEncoded
    @POST("reg")
    fun userRegister(@Field("name") name: String,
                     @Field("username") username: String,
                     @Field("email") email: String,
                     @Field("phone") phone: String,
                     @Field("password") password: String,
                     @Field("password_confirmation") password_confirmation: String) : Call<RegisterResponse>

    @FormUrlEncoded
        @POST("login")
    fun login(@Field("phone") phone: String,
              @Field("password") password: String): Call<LoginResponse>


    @FormUrlEncoded
    @POST("m/person/new")
    fun insuranceEntry(
        @Field("army_status") army_status : Int?=0,
        @Field("office_id") office_id: Int?=0,
        @Field("payment_type") payment_type: String,
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("nrc") nrc: String,
        @Field("father_name") father_name: String,
        @Field("occupation") occupation:String,
        @Field("premium_kyat") premium_kyat: Int,
        @Field("township_id") township_id: Int,
        @Field("dept_id") dept_id: Int,
        @Field("permanent_address") permanent_address:String,
        @Field("date_of_birth") date_of_birth: String,
        @Field("birth_place") birth_place: String,
        @Field("salary") salary: Int,
        @Field("insurance_amount") insurance_amount: Int,
        @Field("insurance_peroid") insurance_peroid: Int,
        @Field("previous_premium_status") previous_premium_status: Int,
        @Field("previous_acceptance_status") previous_acceptance_status: Int,
        @Field("medicial_status[]") medicial_status: ArrayList<Int>,
        @Field("drug_status") drug_status: Int,
        @Field("mark") mark: String,
        @Field("height") height: String,
        @Field("weight") weight: String,
        @Field("user_id") user_id: String,
        @Field("amount") amount: Int,
        @Field("beneficiary_name[]") beneficiary_name: ArrayList<String>,
        @Field("beneficiary_nrc[]") beneficiary_nrc: ArrayList<String>,
        @Field("beneficiary_father_name[]") beneficiary_father_name: ArrayList<String>,
        @Field("relationship[]") relationship: ArrayList<String>,
        @Field("age[]") age: ArrayList<Int>,
        @Field("percentage[]") percentage: ArrayList<Double>,
        @Field("other_beneficiary_description[]") other_beneficiary_description: ArrayList<String>,
        @Field("other_beneficiary_percent[]") other_beneficiary_percent: ArrayList<Double>,
        @Field("phone_no") phone_no:String,
        @Field("previous_policy_id") previous_policy_id:String,
        @Field("start_date") start_date:String?="",
        @Field("age_next_year") age_next_year: Int,
        @Field("health_recommended_letter") health_recommended_letter: String?="",
        @Field("extension") extension: String?="",
        @Field("bank") bank: Int?=null,
        @Field("easy_pay") easy_pay: Int?=null,
        @Field("sh") sh: Int?=null
    ): Call<NewEntryResponse>


    @GET("m/premium")
    fun calculatePremium(
        @Query("age_next_year") age: Int,
        @Query("insurance_peroid") insurance_peroid: Int,
        @Query("insurance_amount") insurance_amount: Int
    ):Call<PremiumResponse>

    @GET("admin/nrcsuggest/{nrcno}")
    fun getNrcById(@Path("nrcno") no: Int):Call<ArrayList<NrcListResponse>>

    @FormUrlEncoded
    @POST("m/policyinfo/add")
    fun MatchUserIdPolicyId(
        @Field("user_id") user_id: Int,
        @Field("policy_id") policy_id: String): Call<MatchUserPolicyResponse>


    @FormUrlEncoded
    @POST("dept/historyupdate")
    fun jobHistoryUpdate(
        @Field("insurance_person_id") insurance_person_id: Int,
        @Field("township") township: Int,
        @Field("dept_address") dept_address: Int,
        @Field("occupation") occupation: String,
        @Field("army_status") army : String
    ): Call<UpdateJobHistoryResponse>

    @POST("m/policyinfo/add")
    fun joinPolicyId(
        @Query("user_id") user_id : String,
        @Query("policy_id") policy_id : String
    ) : Call<JoinPolicy>


}