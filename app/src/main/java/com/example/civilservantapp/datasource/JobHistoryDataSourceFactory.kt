package com.example.civilservantapp.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.civilservantapp.model.profile.HistoryData
import com.hadilq.liveevent.LiveEvent

class JobHistoryDataSourceFactory(val insurance_person_id: Int): DataSource.Factory<Int, HistoryData>() {
    val jobHistoryLiveDataSource = MutableLiveData<JobHistoryDataSource>()
    override fun create(): DataSource<Int, HistoryData> {
        val jobHistoryDataSource = JobHistoryDataSource(insurance_person_id)
        jobHistoryLiveDataSource.postValue(jobHistoryDataSource)
        return jobHistoryDataSource
    }
}