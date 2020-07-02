package com.example.civilservantapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.civilservantapp.api.FetchDataApi
import com.example.civilservantapp.api.HostRetrofit
import com.example.civilservantapp.api.LifeInsuranceApi
import com.example.civilservantapp.model.*
import com.example.civilservantapp.model.SubStractList.*
import com.example.civilservantapp.model.joinpolicy.JoinPolicy
import com.example.civilservantapp.model.profile.UpdateJobHistoryResponse
import com.google.gson.Gson
import com.hadilq.liveevent.LiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class LifeInsuranceViewModel: ViewModel() {
    var TAG : String = "LifeInsuranceViewModel"
    var amtList : MutableLiveData<ArrayList<InsuranceAmtResponse>> = MutableLiveData()
    var medicalList: LiveEvent<NetWorkViewState<ArrayList<Disease>>> = LiveEvent()
    var officeList: LiveEvent<NetWorkViewState<ArrayList<OfficeResponse>>> = LiveEvent()

    var registData: MutableLiveData<NetWorkViewState<RegisterResponse>> = MutableLiveData()
    var loginData: MutableLiveData<NetWorkViewState<LoginResponse>> = MutableLiveData()
    var entryData: MutableLiveData<NetWorkViewState<NewEntryResponse>> = MutableLiveData()
    var depositAmt: MutableLiveData<NetWorkViewState<PremiumResponse>> = MutableLiveData()
    var nrcListData: LiveEvent<NetWorkViewState<ArrayList<NrcListResponse>>> = LiveEvent()

    var stateRegionData: LiveEvent<NetWorkViewState<ArrayList<State_RegionResponse>>> = LiveEvent()
    var districtData: LiveEvent<NetWorkViewState<DistrictResponse>> = LiveEvent()
    var townshipData: LiveEvent<NetWorkViewState<TownshipResponse>> = LiveEvent()
    var ministryData: LiveEvent<NetWorkViewState<ArrayList<MinistryResponse>>> = LiveEvent()
    var departmentData: LiveEvent<NetWorkViewState<DepartmentResponse>> = LiveEvent()
    var matchData: LiveEvent<NetWorkViewState<MatchUserPolicyResponse>> = LiveEvent()
    var relationshipData: LiveEvent<NetWorkViewState<ArrayList<RelationshipResponse>>> = LiveEvent()
    var joinPolicyData : LiveEvent<NetWorkViewState<JoinPolicy>> = LiveEvent()

    var updateJobHistoryData: LiveEvent<NetWorkViewState<UpdateJobHistoryResponse>> = LiveEvent()

    fun getPremium(age: Int, insurance_peroid: Int, insurance_amount: Int){
        val client = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        depositAmt.postValue(NetWorkViewState.loading(true))
        client.calculatePremium(age, insurance_peroid, insurance_amount)
            .enqueue(object : Callback<PremiumResponse>{
                override fun onFailure(call: Call<PremiumResponse>, t: Throwable) {
                    Log.d(TAG, t.message)
                    depositAmt.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<PremiumResponse>, response: Response<PremiumResponse>) {
                    if(response.isSuccessful){
                        Log.d(TAG, Gson().toJson(response.body()!!))
                        depositAmt.postValue(NetWorkViewState.success(response.body()!!))
                    }
                }
            })
    }

    fun getNrcById(id: Int){
        val client = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        nrcListData.postValue(NetWorkViewState.loading(true))
        client.getNrcById(id).enqueue(object : Callback<ArrayList<NrcListResponse>>{
            override fun onFailure(call: Call<ArrayList<NrcListResponse>>, t: Throwable) {
                nrcListData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ArrayList<NrcListResponse>>, response: Response<ArrayList<NrcListResponse>>) {
                if(response.isSuccessful){
                    nrcListData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    nrcListData.postValue(NetWorkViewState.error(HttpException(response)))
                }

            }
        })
    }
    /*
    get amount list
     */
    fun getInsuranceAmtList(){
        val client = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        client.getInsuranceAmount().enqueue(object : Callback<ArrayList<InsuranceAmtResponse>>{
            override fun onFailure(call: Call<ArrayList<InsuranceAmtResponse>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<InsuranceAmtResponse>>,
                response: Response<ArrayList<InsuranceAmtResponse>>
            ) {
                Log.d(TAG, Gson().toJson(response.body()))
                amtList.postValue(response.body())
            }
        })
    }

    /*
    get Medical LIst
     */
    fun getMedicalList(){
        val client = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        medicalList.postValue(NetWorkViewState.loading(true))
        client.getMedicalList().enqueue(object : Callback<ArrayList<Disease>>{
            override fun onFailure(call: Call<ArrayList<Disease>>, t: Throwable) {
                Log.d(TAG, "medicallist\n"+t.message.toString())
                medicalList.postValue(NetWorkViewState.error(t))
            }
            override fun onResponse(call: Call<ArrayList<Disease>>, response: Response<ArrayList<Disease>>) {

                if(response.isSuccessful){
                    medicalList.postValue(NetWorkViewState.success(response.body()!!))
                    Log.d(TAG, Gson().toJson(response.body()))
                }else{
                    medicalList.postValue(NetWorkViewState.error(HttpException(response)))
                }

            }
        }
        )
    }

    /*
    Register User
     */
    fun RegisterUser(name: String, username: String, email: String, phone: String, password: String, confirmpassword: String){
        val registerClient = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        registData.postValue(NetWorkViewState.loading(true))
        registerClient.userRegister(name, username, email, phone, password, confirmpassword)
            .enqueue(object : Callback<RegisterResponse>{
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    registData.postValue(NetWorkViewState.success(response.body()!!))
                    Log.d(TAG, Gson().toJson(response.body()))
                }
            })
    }

    /*
    Login USer
     */
    fun LoginUser(phone: String, password: String){
        val loginClient = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        loginData.postValue(NetWorkViewState.loading(true))
        loginClient.login(phone, password)
            .enqueue(object : Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d(TAG, Gson().toJson(response.body()))
                        loginData.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        loginData.postValue(NetWorkViewState.error(HttpException(response)))
                    }

                }
            })
    }

    /*
    Get Office List
     */
    fun getOfficeList(){
        val officeListClient = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        officeList.postValue(NetWorkViewState.loading(true))
        officeListClient.getOfficeList().enqueue(object : Callback<ArrayList<OfficeResponse>>{
            override fun onFailure(call: Call<ArrayList<OfficeResponse>>, t: Throwable) {
                officeList.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ArrayList<OfficeResponse>>, response: Response<ArrayList<OfficeResponse>>) {
                if(response.isSuccessful){
                    officeList.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    officeList.postValue(NetWorkViewState.error(HttpException(response)))
                }

            }
        })
    }

    fun get_StateRegionData(){
        val client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        stateRegionData.postValue(NetWorkViewState.loading(true))
        client.getStateRegion().enqueue(object : Callback<ArrayList<State_RegionResponse>>{
            override fun onFailure(call: Call<ArrayList<State_RegionResponse>>, t: Throwable) {
                stateRegionData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ArrayList<State_RegionResponse>>, response: Response<ArrayList<State_RegionResponse>>
            ) {
                if(response.isSuccessful){
                    stateRegionData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    stateRegionData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun get_DistrictData(stateregionid: Int){
        val client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        districtData.postValue(NetWorkViewState.loading(true))
        client.getDistrict(stateregionid).enqueue(object : Callback<DistrictResponse>{
            override fun onFailure(call: Call<DistrictResponse>, t: Throwable) {
                districtData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<DistrictResponse>, response: Response<DistrictResponse>) {
                if(response.isSuccessful){
                    districtData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    districtData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun get_TownshipData(districtid: Int){
        val client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        townshipData.postValue(NetWorkViewState.loading(true))
        client.getTownship(districtid).enqueue(object : Callback<TownshipResponse>{
            override fun onFailure(call: Call<TownshipResponse>, t: Throwable) {
                townshipData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<TownshipResponse>, response: Response<TownshipResponse>) {
                if(response.isSuccessful){
                    townshipData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    townshipData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun get_MinistryData(){
        val client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        ministryData.postValue(NetWorkViewState.loading(true))
        client.getMinistry().enqueue(object : Callback<ArrayList<MinistryResponse>>{
            override fun onFailure(call: Call<ArrayList<MinistryResponse>>, t: Throwable) {
                ministryData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<ArrayList<MinistryResponse>>, response: Response<ArrayList<MinistryResponse>>) {
                if(response.isSuccessful){
                    ministryData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    ministryData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    fun get_DepartmentData(ministryid: Int){
        val client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        departmentData.postValue(NetWorkViewState.loading(true))
        client.getDepartment(ministryid).enqueue(object : Callback<DepartmentResponse>{
            override fun onFailure(call: Call<DepartmentResponse>, t: Throwable) {
                departmentData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<DepartmentResponse>, response: Response<DepartmentResponse>) {
                if(response.isSuccessful){
                    Log.d(TAG, Gson().toJson(response.body()))
                    departmentData.postValue(NetWorkViewState.success(response.body()!!))
                }else{
                    departmentData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }

    /*
    Entry of New Insurance Person
     */
    fun newEntry(army_status : Int?,office_id: Int?, payment_type: String, name: String, gender: String, nrc: String, father_name: String,
                 occupation:String,premium_kyat: Int,township_id: Int, dept_id: Int , permanent_address:String, date_of_birth: String, birth_place: String,
                 salary: Int, insurance_amount: Int, insurance_peroid: Int, previous_premium_status: Int,
                 previous_acceptance_status: Int, medicial_status: ArrayList<Int>, drug_status: Int,
                 mark: String, height: String, weight: String, user_id: String, amount: Int, beneficiary_name: ArrayList<String>,
                 beneficiary_nrc: ArrayList<String>, beneficiary_father_name: ArrayList<String>,
                 relationship: ArrayList<String>, age: ArrayList<Int>, percentage: ArrayList<Double>,
                 other_beneficiary_description: ArrayList<String>, other_beneficiary_percent: ArrayList<Double>,
                 phone_no: String, previous_policy_id: String, age_next_year: Int, health_recommended_letter: String?, extension: String?,
                 bank: Int?, easy_pay: Int?, sh: Int?){
        val entry = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        entryData.postValue(NetWorkViewState.loading(true))
        entry.insuranceEntry(army_status,office_id, payment_type, name, gender, nrc, father_name, occupation, premium_kyat, township_id, dept_id, permanent_address,
            date_of_birth, birth_place, salary, insurance_amount, insurance_peroid, previous_premium_status,
            previous_acceptance_status, medicial_status, drug_status, mark, height, weight, user_id,
            amount, beneficiary_name, beneficiary_nrc, beneficiary_father_name, relationship, age, percentage,
            other_beneficiary_description, other_beneficiary_percent, phone_no, previous_policy_id, null, age_next_year,
            health_recommended_letter, extension, bank, easy_pay, sh)
            .enqueue(object : Callback<NewEntryResponse>{
                override fun onFailure(call: Call<NewEntryResponse>, t: Throwable) {
                    entryData.postValue(NetWorkViewState.error(t))
                    Log.d("test" , "error is error ${t.localizedMessage}")
                }

                override fun onResponse(call: Call<NewEntryResponse>, response: Response<NewEntryResponse>) {
                    try{
                        if(response.isSuccessful){
                            Log.d("test","Successful is ${response.body()}")
                            entryData.postValue(NetWorkViewState.success(response.body()!!))
                        }else{
                            Log.d("test","respone is $")
                            entryData.postValue(NetWorkViewState.error(HttpException(response)))
                        }
                    }catch (e: Exception){
                        Log.d(TAG, e.toString())
                    }

                }
            })

    }

    /*
    Match User Id And Policy Id
     */
    fun MatchUserPolicyId(userId: Int, policyId: String){
        val matchClient = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        matchData.postValue(NetWorkViewState.loading(true))
        matchClient.MatchUserIdPolicyId(userId, policyId).enqueue(
            object : Callback<MatchUserPolicyResponse>{
                override fun onFailure(call: Call<MatchUserPolicyResponse>, t: Throwable) {
                    matchData.postValue(NetWorkViewState.error(t))
                    Log.d("test","User PolicyId error" + t.localizedMessage)
                }

                override fun onResponse(call: Call<MatchUserPolicyResponse>, response: Response<MatchUserPolicyResponse>) {
                    if(response.isSuccessful){
                        Log.d("test","Match Policy Response is successfully ${response.body()!!.message} " +
                                "${response.body()!!.user_id}  ${response.body()!!.policy_id} ")
                        matchData.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        Log.d("test","Match policy id is not successfuly")
                        matchData.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }
            }
        )
    }

    /*
    Get Relationship
     */
    fun getRelationship(){
        val relationshipClient = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
        relationshipData.postValue(NetWorkViewState.loading(true))
        relationshipClient.getRelationship().enqueue(
            object : Callback<ArrayList<RelationshipResponse>>{
                override fun onFailure(call: Call<ArrayList<RelationshipResponse>>, t: Throwable) {
                    relationshipData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(
                    call: Call<ArrayList<RelationshipResponse>>,
                    response: Response<ArrayList<RelationshipResponse>>
                ) {
                    if(response.isSuccessful){
                        relationshipData.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        relationshipData.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }
            }
        )
    }

    /*
    Update Job History
     */
    fun updateJobHistory(insurance_person_id: Int, township: Int,dept_address: Int, occupation:String,army : String){
        val updateJobClient = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        updateJobHistoryData.postValue(NetWorkViewState.loading(true))
        updateJobClient.jobHistoryUpdate(insurance_person_id, township, dept_address, occupation,army).enqueue(
            object : Callback<UpdateJobHistoryResponse>{
                override fun onFailure(call: Call<UpdateJobHistoryResponse>, t: Throwable) {
                    updateJobHistoryData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<UpdateJobHistoryResponse>, response: Response<UpdateJobHistoryResponse>) {
                    if(response.isSuccessful){
                        updateJobHistoryData.postValue(NetWorkViewState.success(response.body()!!))
                    }else{
                        updateJobHistoryData.postValue(NetWorkViewState.error(HttpException(response)))
                    }
                }
            }
        )
    }
    fun joinPolicyId(id : String,policy : String){
        val joinPolicy = HostRetrofit().getRetrofit().create(LifeInsuranceApi::class.java)
        joinPolicyData.postValue(NetWorkViewState.loading(true))
        joinPolicy.joinPolicyId(id,policy)
            .enqueue(object : Callback<JoinPolicy>{
                override fun onFailure(call: Call<JoinPolicy>, t: Throwable) {
                    joinPolicyData.postValue(NetWorkViewState.error(t))
                }

                override fun onResponse(call: Call<JoinPolicy>, response: Response<JoinPolicy>) {
                   if(response.isSuccessful)
                       joinPolicyData.postValue(NetWorkViewState.success(response.body()!!))
                    else
                       joinPolicyData.postValue(NetWorkViewState.error(HttpException(response)))
                }

            })
    }


}