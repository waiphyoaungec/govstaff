package com.example.civilservantapp.view.fragment


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar

import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.download_extension.DownloadPdf
import com.example.civilservantapp.download_extension.ShowAlert
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.rest.RestModel
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.fragment_closed.*

class ClosedFragment : Fragment() {
    val fetchDataViewModel : FetchDataViewModel by lazy{
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    lateinit var id : String
    lateinit var policy_id : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var amount : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_closed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
