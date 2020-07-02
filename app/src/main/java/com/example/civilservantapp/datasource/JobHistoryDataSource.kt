package com.example.civilservantapp.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.civilservantapp.api.FetchDataApi
import com.example.civilservantapp.api.HostRetrofit
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.profile.HistoryData
import com.example.civilservantapp.model.profile.JobHistoryResponse
import com.google.gson.Gson
import com.hadilq.liveevent.LiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class JobHistoryDataSource(val insurance_person_id: Int) : PageKeyedDataSource<Int, HistoryData>(){
    var client: FetchDataApi
    var jobHistoryData = MutableLiveData<NetWorkViewState<ArrayList<HistoryData>>>()
    companion object{
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 5
    }
    init {
        client = HostRetrofit().getRetrofit().create(FetchDataApi::class.java)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HistoryData>) {
        Log.d("Profileloadafter", "in")
        jobHistoryData.postValue(NetWorkViewState.loading(true))
        client.getDepHistory(insurance_person_id, params.key).enqueue(object : Callback<JobHistoryResponse>{
            override fun onFailure(call: Call<JobHistoryResponse>, t: Throwable) {
                Log.d("Profileloadafter", t.message.toString())
                jobHistoryData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<JobHistoryResponse>, response: Response<JobHistoryResponse>) {
                Log.d("Profileloadafter", Gson().toJson(response.body()))
                if(response.isSuccessful){
                    jobHistoryData.postValue(NetWorkViewState.success(response.body()!!.data!!))
                    callback.onResult(response.body()!!.data!!, params.key+1)
                    Log.d("ProfileActloadinitial", Gson().toJson(response.body()))
                }else{
                    Log.d("Profileloadafter", HttpException(response).message.toString())
                    jobHistoryData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HistoryData>) {

    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, HistoryData>) {
        Log.d("Profileloadafter", "in")
        jobHistoryData.postValue(NetWorkViewState.loading(true))
        client.getDepHistory(insurance_person_id, FIRST_PAGE).enqueue(object : Callback<JobHistoryResponse>{
            override fun onFailure(call: Call<JobHistoryResponse>, t: Throwable) {
                jobHistoryData.postValue(NetWorkViewState.error(t))
            }

            override fun onResponse(call: Call<JobHistoryResponse>, response: Response<JobHistoryResponse>) {
                if(response.isSuccessful){
                    /*
                    adding headervalue in arraylist
                     */
                    val historyresponse : ArrayList<HistoryData> = response.body()!!.data!!
                    historyresponse.add(0, HistoryData(0, 0, "အလုပ်အကိုင်အပြည့်အစုံ", 0, 0, 0, ""
            , "", "", "", "ရုံး/လုပ်ငန်းဌာန", "ဝန်ကြီးဌာန", "ပြည်နယ်/တိုင်း", "ခရိုင်",
                "မြို့နယ်"))
                    jobHistoryData.postValue(NetWorkViewState.success(historyresponse))
                    callback.onResult(response.body()!!.data!!, null, FIRST_PAGE+1)
                    Log.d("ProfileActloadinitial", Gson().toJson(response.body()))
                }else{
                    jobHistoryData.postValue(NetWorkViewState.error(HttpException(response)))
                }
            }
        })
    }
}