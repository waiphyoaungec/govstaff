package com.example.civilservantapp.api

import com.example.civilservantapp.model.RelationshipResponse
import com.example.civilservantapp.model.SubStractList.*
import com.example.civilservantapp.model.contract.ContractResponse
import com.example.civilservantapp.model.dead.Dead
import com.example.civilservantapp.model.dead.SendDeadForm
import com.example.civilservantapp.model.loan.Loan
import com.example.civilservantapp.model.loan.SendLoansuccess
import com.example.civilservantapp.model.password.ChangePasswordResponse
import com.example.civilservantapp.model.password.CheckPhoneResponse
import com.example.civilservantapp.model.payment.EntryMonthlyFee
import com.example.civilservantapp.model.payment.FeeResponse
import com.example.civilservantapp.model.payment.MonthyFeeResponse
import com.example.civilservantapp.model.profile.JobHistoryResponse
import com.example.civilservantapp.model.profile.PremiumstatusResponse
import com.example.civilservantapp.model.refund.DoRefund
import com.example.civilservantapp.model.refund.Refund
import com.example.civilservantapp.model.rest.RestModel
import com.example.civilservantapp.model.rest.SendRest
import okhttp3.MultipartBody
import com.example.civilservantapp.model.fullyear.FullModel
import com.example.civilservantapp.model.fullyear.SendFullYear
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FetchDataApi {
    @GET("state/state_region")
    fun getStateRegion(): Call<ArrayList<State_RegionResponse>>

    @GET("state/district/{stateregionid}")
    fun getDistrict(@Path("stateregionid") stateregionid: Int): Call<DistrictResponse>

    @GET("state/township/{districtid}")
    fun getTownship(@Path("districtid") districtid: Int): Call<TownshipResponse>

    @GET("office/ministry")
    fun getMinistry(): Call<ArrayList<MinistryResponse>>

    @GET("office/dept/{ministryid}")
    fun getDepartment(@Path("ministryid") ministryid: Int): Call<DepartmentResponse>

    @GET("c/relationship")
    fun getRelationship(): Call<ArrayList<RelationshipResponse>>

    @GET("m/monthly/fee")
    fun getMonthlyFee(@Query("policy_id") policy_id: String): Call<MonthyFeeResponse>

    @FormUrlEncoded
    @POST("m/monthly/fee")
    fun postMonthlyFee(
        @Field("payment_type")
        payment_type: String,
        @Field("insurance_person_id")
        insurance_person_id: Int,
        @Field("office_id")
        office_id: Int,
        @Field("nrc")
        nrc: String,
        @Field("easy_pay")
        easy_pay: Int,
        @Field("bank")
        bank: Int,
        @Field("sh")
        sh: Int,
        @Field("monthly_date")
        monthly_date: String,
        @Field("due_date")
        due_date: String,
        @Field("amount")
        amount: Int
    ):Call<EntryMonthlyFee>

    @GET("c/getcontract")
    fun getContract(
        @Query("policy_id") policy_id: String
    ): Call<ContractResponse>

    @GET("premiums/premium")
    fun getPayAmount(
        @Query("num_month") num_month: Int,
        @Query("age") age: Int,
        @Query("insurance_peroid") insurance_peroid: Int,
        @Query("insurance_amount") insurance_amount: Int
    ): Call<Int>

    @GET("c/fee")
    fun getFee():Call<FeeResponse>

    @GET("m/premium/status")
    fun getUserProfile(
        @Query("user_id") user_id: Int
    ): Call<PremiumstatusResponse>

    @GET("check/phone")
    fun CheckPhone(
        @Query("phone") phone: String
    ): Call<CheckPhoneResponse>

    @FormUrlEncoded
    @POST("change/pass")
    fun ResetPassword(
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("data") data: String
    ): Call<ChangePasswordResponse>

    @GET("dept/history")
    fun getDepHistory(
        @Query("insurance_person_id") insurance_person_id: Int,
        @Query("page") page: Int
    ): Call<JobHistoryResponse>

    @GET("claim/loan")
    fun checkLoan(
        @Query("policy_id") policy_id : String
    ) : Call<Loan>
    @POST("loan/store")
    fun sendLoan(
        @Query("policy_id") policy_id: String,
        @Query("insurance_person_id") insurance_person_id: Int,
        @Query("loan_amount") loan_amount : String,
        @Query("claim_id") claim_id : Int,
        @Query("witness_name") witness_name : String,
        @Query("witness_nrc") witness_nrc : String,
        @Query("witness_address") witness_address : String,
        @Query("witness_phone") witness_phone : String,
        @Query("want_loan_amt") want_loan_amt : String

    ) : Call<SendLoansuccess>
   @GET("claim/refund")
   fun checkRefund(
       @Query("policy_id") policy_id: String,
       @Query("resignation_date") resignation : String,
       @Query("lost") lost : String
   ) : Call<Refund>
  @Multipart
  @POST("refund/store")
  fun doRefund(
      @Part("insurance_person_id") personId : RequestBody ,
      @Part("claim_amount") claim_amount : RequestBody ,
      @Part  registerLetter : MultipartBody.Part,
      @Part("extension") extension : RequestBody ,
      @Part("resignation_date") date : RequestBody,
      @Part("loan") loan : RequestBody,
      @Part("loan_interest") loanInterest : RequestBody,
      @Part("premium") premium : RequestBody,
      @Part("persistent_interest") persistent : RequestBody,
      @Part("contract_fines") contract_fine : RequestBody,
      @Part("contract_copy") contract_copy : RequestBody,
      @Part("lost_contract_stamp") lost_constract_stamp : RequestBody


  ) : Call<DoRefund>
    @GET("claim/compensan")
    fun checkDie(
        @Query("policy_id") policy_id : String,
        @Query("dead_date") dead_date : String
    ) :Call<Dead>
    @Multipart
    @POST("compensan/store ")
    fun sendDead(
        @Part("insurance_person_id") ins_id : RequestBody,
        @Part("claim_amount") cal_amt : RequestBody,
        @Part("extension") amt : RequestBody,
        @Part("dead_certificate") dead_certificate:RequestBody,
        @Part("dead_date") dead_date : RequestBody

    ) : Call<SendDeadForm>

    @GET("claim/close")
    fun checkRest(
        @Query("policy_id") id : String
    ) : Call<RestModel>
    @POST("close/store")
    fun sendRest(
     @Query("insurance_person_id") person_id : String,
     @Query("claim_amount") calim_amout : String
    ) : Call<SendRest>
    @GET("claim/fullyear")
    fun checkFullYear(
        @Query("policy_id") id : String,
        @Query("lost_contract") lost_contract : String
    ) : Call<FullModel>
    @POST("fullyear/store")
    fun sendFullYear(
        @Query("insurance_person_id") person_id : String,
        @Query("claim_amount") calim_amout : String,
        @Query("loan") loan : String,
        @Query("loan_interest") loan_interest : String,
        @Query("premium") premium: String,
        @Query("persistent_interest") persistent: String,
        @Query("contract_fines") contract_fine: String,
        @Query("contract_copy") contract_copy : String,
        @Query("lost_contract_stamp") lost_contract_stamp : String


    ) : Call<SendFullYear>

}