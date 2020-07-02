package com.example.civilservantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.civilservantapp.datasource.JobHistoryDataSource
import com.example.civilservantapp.datasource.JobHistoryDataSourceFactory
import com.example.civilservantapp.model.profile.HistoryData

class JobHistoryViewModel(val insurance_person_id: Int): ViewModel() {
    private var liveDataSource : LiveData<JobHistoryDataSource>
    var jobHistoryPageList : LiveData<PagedList<HistoryData>>
    init {
        val itemDataSourceFactory = JobHistoryDataSourceFactory(insurance_person_id)
        liveDataSource = itemDataSourceFactory.jobHistoryLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(JobHistoryDataSource.PAGE_SIZE)
            .build()
        jobHistoryPageList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}