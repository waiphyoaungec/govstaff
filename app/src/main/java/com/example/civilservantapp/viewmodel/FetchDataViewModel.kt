package com.example.civilservantapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.civilservantapp.api.FetchDataApi
import com.example.civilservantapp.api.HostRetrofit
import com.example.civilservantapp.datasource.JobHistoryDataSource
import com.example.civilservantapp.datasource.JobHistoryDataSourceFactory
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.contract.ContractResponse
import com.example.civilservantapp.model.dead.Dead
import com.example.civilservantapp.model.dead.SendDeadForm
import com.example.civilservantapp.model.fullyear.FullModel
import com.example.civilservantapp.model.fullyear.SendFullYear
import com.example.civilservantapp.model.loan.Loan
import com.example.civilservantapp.model.loan.SendLoansuccess
import com.example.civilservantapp.model.password.ChangePasswordResponse
import com.example.civilservantapp.model.password.CheckPhoneResponse
import com.example.civilservantapp.model.payment.EntryMonthlyFee
import com.example.civilservantapp.model.payment.FeeResponse
import com.example.civilservantapp.model.payment.MonthyFeeResponse
import com.example.civilservantapp.model.profile.HistoryData
import com.example.civilservantapp.model.profile.PremiumstatusResponse
import com.example.civilservantapp.model.refund.DoRefund
import com.example.civilservantapp.model.refund.Refund
import com.example.civilservantapp.model.rest.RestModel
import com.example.civilservantapp.model.rest.SendRest
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hadilq.liveevent.LiveEvent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File


class FetchDataViewModel: ViewModel() {
    var TAG: String ="FetchDataViewModel"
    var contractData: LiveEvent<NetWorkViewState<ContractResponse>> = LiveEvent()
    var getmonthlyFeeData: LiveEvent<NetWorkViewState<MonthyFeeResponse>> = LiveEvent()
    var postmonthlyFeeData: LiveEvent<NetWorkViewState<EntryMonthlyFee>> = LiveEvent()
    var getPayAmount: LiveEvent<NetWorkViewState<Int>> = LiveEvent()
    var getFee: LiveEvent<NetWorkViewState<FeeResponse>> = LiveEvent()
    var getUserProfile: LiveEvent<NetWorkViewState<PremiumstatusResponse>> = LiveEvent()
    var checkPhoneData: LiveEvent<NetWorkViewState<CheckPhoneResponse>> = LiveEvent()
    var changePasswordData: LiveEvent<NetWorkViewState<ChangePasswordResponse>> = LiveEvent()
    lateinit var liveDataSource : LiveData<JobHistoryDataSource>
    lateinit var jobHistoryPageList : LiveData<PagedList<HistoryData>>
    var checkLoan : LiveEvent<NetWorkViewState<Loan>> = LiveEvent()
    var sendLoan : LiveEvent<NetWorkViewState<SendLoansuccess>> = LiveEvent()
    var client: FetchDataApi
    var checkRefund : LiveEvent<NetWorkViewState<Refund>> = LiveEvent()
    var doRefund : LiveEvent<NetWorkViewState<DoRefund>> = LiveEvent()
    var checkDead : LiveEvent<NetWorkViewState<Dead>> = LiveEvent()
    var sendDead : LiveEvent<NetWorkViewState<SendDeadForm>> = LiveEvent()
    var checkRest : LiveEvent<NetWorkViewState<RestModel>> = LiveEvent()
    var sendRest : LiveEvent<NetWorkViewState<SendRest>> = LiveEvent()
    var fullcheck : LiveEvent<NetWorkViewState<FullModel>> = LiveEvent()
    var sendfull : LiveEvent<NetWorkViewState<SendFullYear>> = LiveEvent()
    init {
        client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
    }



    fun sendLoan(policy_id: String,insurance_person_id : Int,loan_amount : String,claimId:Int,witness_name: String,
                 witness_nrc : String,witness_address : String,witness_phone : String,wantloanamt : String){
        sendLoan.postValue(NetWorkViewState.loading(true))
        client.sendLoan(policy_id,insurance_person_id,loan_amount,claimId,witness_name,
            witness_nrc,witness_address,witness_phone,wantloanamt)
            .enqueue(object  : Callback<SendLoansuccess>{
                override fun onFailure(call: Call<SendLoansuccess>, t: Throwable) {
                    Log.d("test","is Fail ${t.localizedMessage}")
                    sendLoan.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<SendLoansuccess>, response: Response<SendLoansuccess>) {
                   if(response.isSuccessful){
                       Log.d("test","is successfully")
                      val loanresponse = response.body()
                       sendLoan.postValue(NetWorkViewState.success(loanresponse!!))
                   }
                }

            })
    }
    fun checkLoan(policy_id: String){
        checkLoan.postValue(NetWorkViewState.loading(true))
        client.checkLoan(policy_id).enqueue(object : Callback<Loan>{
            override fun onFailure(call: Call<Loan>, t: Throwable) {
               checkLoan.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<Loan>, response: Response<Loan>) {
                if(response.isSuccessful) {
                    val loan = response.body()
                    var gson = GsonBuilder().create()
                    if(response.body()!!.message.equals("success")){
                        val responseTypeToken: TypeToken<Loan.Data> =
                            object : TypeToken<Loan.Data>() {}
                        var data = gson.fromJson<Loan.Data>(gson.toJson(loan!!.data),responseTypeToken.type)
                        var loanModel = Loan(loan.message,data,loan.paidMonth,loan.depositedAmt,loan.loanamt,
                            loan.stamp,loan.totloan)
                        checkLoan.postValue(NetWorkViewState.success(loanModel))
                    }
                    else{
                        var fail_data = loan!!.data.toString()
                        var loanModel = Loan(loan.message,fail_data,loan.paidMonth,loan.depositedAmt,loan.loanamt,
                            loan.stamp,loan.totloan)
                        checkLoan.postValue(NetWorkViewState.success(loanModel))
                    }

                }
                else
                    checkLoan.postValue(NetWorkViewState.error(HttpException(response)))
            }

        })
    }
    fun checkReund(policy_id : String,regi_data : String,lost : String){
        checkRefund.postValue(NetWorkViewState.loading(true))
        client.checkRefund(policy_id,regi_data,lost)
            .enqueue(object : Callback<Refund>{
                override fun onFailure(call: Call<Refund>, t: Throwable) {
                    Log.d("test","error ${t.localizedMessage}")
                    checkRefund.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<Refund>, response: Response<Refund>) {
                    Log.d("test","ok")
                    if(response.isSuccessful){
                        val refund = response.body()
                        val gson = GsonBuilder().create()
                        if(response.body()!!.message.equals("success")){
                            val responseTypeToken: TypeToken<Refund.Data> =
                                object : TypeToken<Refund.Data>() {}
                            val data = gson.fromJson<Refund.Data>(gson.toJson(refund!!.data),responseTypeToken.type)
                            val refundModel = Refund(refund.message,data,refund.paidMonth,refund.depositedAmt,
                                refund.claimAmount,refund.deductData)
                            checkRefund.postValue(NetWorkViewState.success(refundModel))
                        }
                        else{
                            val fail_data = refund!!.data.toString()
                            var refundModel = Refund(refund.message,fail_data,refund.paidMonth,refund.depositedAmt,
                                refund.claimAmount,refund.deductData)
                            checkRefund.postValue(NetWorkViewState.success(refundModel))
                        }

                    }
                    else{
                        checkRefund.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }

            })
    }
    fun doRefund(id : String,amt: String,files : File ,extension : String,dt :String,loan : String,loanInterest : String,premium : String,
    persistanceInterest : String, contractFine : String, contractCopy : String,lost_contract_stamp : String){
      doRefund.postValue(NetWorkViewState.loading(true))
        val file =  files
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("resignation_letter", "reg_letter", requestFile)
        val personId = id.toRequestBody("text/plain".toMediaTypeOrNull())
        val amount = amt.toRequestBody("text/plain".toMediaTypeOrNull())
        val type = extension.toRequestBody("text/plain".toMediaTypeOrNull())
        val date = dt.toRequestBody("text/plain".toMediaTypeOrNull())
        val loans = loan.toRequestBody("text/plain".toMediaTypeOrNull())
        val loanInterests = loanInterest.toRequestBody("text/plain".toMediaTypeOrNull())
        val premiums = premium.toRequestBody("text/plain".toMediaTypeOrNull())
        val persistanceInterests = persistanceInterest.toRequestBody("text/plain".toMediaTypeOrNull())
        val contractFines = contractFine.toRequestBody("text/plain".toMediaTypeOrNull())
        val contractCopys = contractCopy.toRequestBody("text/plain".toMediaTypeOrNull())
        val lost_contract_stamps = lost_contract_stamp.toRequestBody("text/plain".toMediaTypeOrNull())
        client.doRefund(personId,amount,body,type,date,loans,loanInterests,premiums,persistanceInterests,contractFines,contractCopys,
        lost_contract_stamps)
            .enqueue(object : Callback<DoRefund>{
                override fun onFailure(call: Call<DoRefund>, t: Throwable) {
                   doRefund.postValue(NetWorkViewState.error(t))
                    Log.d("test",t.localizedMessage!!)
                    doRefund.postValue(NetWorkViewState.error(t))
                }
                override fun onResponse(call: Call<DoRefund>, response: Response<DoRefund>) {
                  Log.d("test",response.body().toString())
                    if(response.isSuccessful)
                    doRefund.postValue(NetWorkViewState.success(response.body()!!))
                    else
                        doRefund.postValue(NetWorkViewState.error(HttpException(response)))
                }

            })


    }

    fun getContract(policyid: String){
        contractData.postValue(NetWorkViewState.loading(true))
        client.getContract(policyid).enqueue(object : Callback<ContractResponse>{
            override fun onFailure(call: Call<ContractResponse>, t: Throwable) {
                contractData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ContractResponse>, response: Response<ContractResponse>) {
                if(response.isSuccessful){
                    contractData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    contractData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun getMonthlyFee(policy_id: String){
        getmonthlyFeeData.postValue(NetWorkViewState.loading(true))
        client.getMonthlyFee(policy_id).enqueue(object : Callback<MonthyFeeResponse>{
            override fun onFailure(call: Call<MonthyFeeResponse>, t: Throwable) {
                getmonthlyFeeData.postValue(NetWorkViewState.error(t))
                Log.d("test","monthly pay ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<MonthyFeeResponse>, response: Response<MonthyFeeResponse>) {
                if(response.isSuccessful){
                    getmonthlyFeeData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    Log.d("test","failed")
                    getmonthlyFeeData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun postMonthlyFee(payment_type: String, insurance_person_id: Int, office_id: Int, nrc: String,
                       easy_pay: Int, bank: Int, sh: Int, monthly_date: String, due_date: String, amount: Int){
        postmonthlyFeeData.postValue(NetWorkViewState.loading(true))
        client.postMonthlyFee(payment_type, insurance_person_id, office_id, nrc, easy_pay, bank, sh, monthly_date, due_date, amount)
            .enqueue(object : Callback<EntryMonthlyFee>{
                override fun onFailure(call: Call<EntryMonthlyFee>, t: Throwable) {
                    postmonthlyFeeData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<EntryMonthlyFee>, response: Response<EntryMonthlyFee>) {
                    if(response.isSuccessful){
                       postmonthlyFeeData.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        postmonthlyFeeData.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }
            })
    }

    fun getPayAmount(num_month: Int, age: Int, insurance_peroid: Int, insurance_amount: Int){
        getPayAmount.postValue(NetWorkViewState.loading(true))
        client.getPayAmount(num_month, age, insurance_peroid, insurance_amount)
            .enqueue(object : Callback<Int>{
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    getPayAmount.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if(response.isSuccessful){
                        getPayAmount.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        getPayAmount.postValue(NetWorkViewState.error(HttpException(response)))
                    }

                }
            })

    }

    fun getFee(){
        getFee.postValue(NetWorkViewState.loading(true))
        client.getFee().enqueue(object : Callback<FeeResponse>{
            override fun onFailure(call: Call<FeeResponse>, t: Throwable) {
                getFee.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<FeeResponse>, response: Response<FeeResponse>) {
                if(response.isSuccessful){
                    getFee.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    getFee.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun getUserProfile(user_id: Int){
        getUserProfile.postValue(NetWorkViewState.loading(true))
        client.getUserProfile(user_id).enqueue(object : Callback<PremiumstatusResponse>{
            override fun onFailure(call: Call<PremiumstatusResponse>, t: Throwable) {
                getUserProfile.postValue(NetWorkViewState.error(t))
                Log.d("ProfileActivity","Failure")
            }

            override fun onResponse(call: Call<PremiumstatusResponse>, response: Response<PremiumstatusResponse>) {

                if(response.isSuccessful){
                    getUserProfile.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    getUserProfile.postValue(NetWorkViewState.error(HttpException(response)))
                    Log.d("ProfileActivity","Success")
                }
            }
        })
    }

    fun checkPhone(phone: String){
        checkPhoneData.postValue(NetWorkViewState.loading(true))
        client.CheckPhone(phone).enqueue(object : Callback<CheckPhoneResponse>{
            override fun onFailure(call: Call<CheckPhoneResponse>, t: Throwable) {
                checkPhoneData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<CheckPhoneResponse>, response: Response<CheckPhoneResponse>) {
                if(response.isSuccessful){
                    checkPhoneData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    checkPhoneData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun resetPassword(password: String, password_confirmation: String, data: String){
        changePasswordData.postValue(NetWorkViewState.loading(true))
        client.ResetPassword(password, password_confirmation, data).enqueue(object : Callback<ChangePasswordResponse>{
            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                changePasswordData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if(response.isSuccessful){
                    changePasswordData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    changePasswordData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun getHistory(insurance_person_id: Int){
        val itemDataSourceFactory = JobHistoryDataSourceFactory(insurance_person_id)
        liveDataSource = itemDataSourceFactory.jobHistoryLiveDataSource
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .build()
        jobHistoryPageList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
    fun checkDead(policy_id : String,dead_date : String){
        checkDead.postValue(NetWorkViewState.loading(true))
        client.checkDie(policy_id,dead_date)
            .enqueue(object : Callback<Dead>{
                override fun onFailure(call: Call<Dead>, t: Throwable) {
                   checkDead.postValue(NetWorkViewState.error(t))
                }
                override fun onResponse(call: Call<Dead>, response: Response<Dead>) {
                  if(response.isSuccessful){
                      val dead = response.body()
                      val gson = GsonBuilder().create()
                      if(response.body()!!.message.equals("success")){
                          val responseTypeToken: TypeToken<Dead.Data> =
                              object : TypeToken<Dead.Data>() {}
                          val data = gson.fromJson<Dead.Data>(gson.toJson(dead!!.data),responseTypeToken.type)
                          val deadModel = Dead(dead.message,data,dead.paidMonth,dead.depositedAmt,dead.insuranceAmount,
                              dead.claimAmount)
                          checkDead.postValue(NetWorkViewState.success(deadModel))
                      }
                      else{
                          val fail_data = dead!!.data.toString()
                          var deadModel = Dead(dead.message,fail_data,dead.paidMonth,dead.depositedAmt,dead.insuranceAmount,
                              dead.claimAmount)
                          checkDead.postValue(NetWorkViewState.success(deadModel))
                      }

                  }
                    else{
                      checkDead.postValue(NetWorkViewState.error(HttpException(response)))
                  }
                }
            })
    }
    fun sendDead(id : String,claimAmmount : String,extension : String,certificate : String,date : String){
        sendDead.postValue(NetWorkViewState.loading(true))
        val personId = id.toRequestBody("text/plain".toMediaTypeOrNull())
        val amt = claimAmmount.toRequestBody("text/plain".toMediaTypeOrNull())
        val ext = extension.toRequestBody("text/plain".toMediaTypeOrNull())
        val certi = certificate.toRequestBody("text/plain".toMediaTypeOrNull())
        val dt =  date.toRequestBody("text/plain".toMediaTypeOrNull())
        client.sendDead(personId,amt,ext,certi,dt)
            .enqueue(object : Callback<SendDeadForm>{
                override fun onFailure(call: Call<SendDeadForm>, t: Throwable) {
                 sendDead.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<SendDeadForm>, response: Response<SendDeadForm>) {
                  if(response.isSuccessful){
                      sendDead.postValue(NetWorkViewState.success(response.body()!!))
                  }
                    else{
                      sendDead.postValue(NetWorkViewState.error(HttpException(response)))
                  }
                }
            })
    }
    fun checkRest(policyId : String){
        checkRest.postValue(NetWorkViewState.loading(true))
        client.checkRest(policyId)
            .enqueue(object : Callback<RestModel>{
                override fun onFailure(call: Call<RestModel>, t: Throwable) {
                   checkRest.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<RestModel>, response: Response<RestModel>) {
                   if(response.isSuccessful){
                       val rest = response.body()
                       val gson = GsonBuilder().create()
                       if(response.body()!!.message.equals("success")){
                           val responseTypeToken: TypeToken<RestModel.Data> =
                               object : TypeToken<RestModel.Data>() {}
                           val data = gson.fromJson<RestModel.Data>(gson.toJson(rest!!.data),responseTypeToken.type)
                           val restModel = RestModel(rest.message,data,rest.paidMonth,rest.depositedAmt,
                               rest.claimAmount)
                           checkRest.postValue(NetWorkViewState.success(restModel))
                       }
                       else{
                           val fail_data = rest!!.data.toString()
                           var deadModel = RestModel(rest.message,fail_data,rest.paidMonth,rest.depositedAmt,
                               rest.claimAmount)
                           checkRest.postValue(NetWorkViewState.success(deadModel))
                       }

                   }

                    else{
                       checkRest.postValue(NetWorkViewState.error(HttpException(response)))
                   }
                }
            })
    }
    fun sendRest(personId : String,amount : String){
        sendRest.postValue(NetWorkViewState.loading(true))
        client.sendRest(personId,amount)
            .enqueue(object : Callback<SendRest>{
                override fun onFailure(call: Call<SendRest>, t: Throwable) {
                    sendRest.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<SendRest>, response: Response<SendRest>) {
                    if(response.isSuccessful){
                        sendRest.postValue(NetWorkViewState.success(response.body()!!))
                    }
                    else{
                        sendRest.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }

            })
    }
    fun checkFullYear(policyId : String){
        fullcheck.postValue(NetWorkViewState.loading(true))
        client.checkFullYear(policyId)
            .enqueue(object : Callback<FullModel>{
                override fun onFailure(call: Call<FullModel>, t: Throwable) {
                    fullcheck.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<FullModel>, response: Response<FullModel>) {
                    if(response.isSuccessful){
                        val rest = response.body()
                        val gson = GsonBuilder().create()
                        if(response.body()!!.message.equals("success")){
                            val responseTypeToken: TypeToken<FullModel.Data> =
                                object : TypeToken<FullModel.Data>() {}
                            val data = gson.fromJson<FullModel.Data>(gson.toJson(rest!!.data),responseTypeToken.type)
                            val restModel = FullModel(rest.message,data,rest.paidMonth,rest.depositedAmt,
                                rest.claimAmount)
                            fullcheck.postValue(NetWorkViewState.success(restModel))
                        }
                        else{
                            val fail_data = rest!!.data.toString()
                            var deadModel = FullModel(rest.message,fail_data,rest.paidMonth,rest.depositedAmt,
                                rest.claimAmount)
                            fullcheck.postValue(NetWorkViewState.success(deadModel))
                        }

                    }

                    else{
                        checkRest.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }
            })
    }
    fun sendFull(personId : String,amount : String){
        sendfull.postValue(NetWorkViewState.loading(true))
        client.sendFullYear(personId,amount)
            .enqueue(object : Callback<SendFullYear>{
                override fun onFailure(call: Call<SendFullYear>, t: Throwable) {
                    sendfull.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<SendFullYear>, response: Response<SendFullYear>) {
                    if(response.isSuccessful){
                        sendfull.postValue(NetWorkViewState.success(response.body()!!))
                    }
                    else{
                        sendfull.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }

            })
    }





}