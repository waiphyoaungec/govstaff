package com.example.civilservantapp.view.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.loan.Loan
import com.example.civilservantapp.view.activity.loan.SendLoyanActivity
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.fragment_loan.*


/**
 * A simple [Fragment] subclass.
 */
class LoanFragment : Fragment() {
    lateinit var sharedPreference: SharedPreference
    lateinit var person_id : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var ctx : Context
    val fetDataViewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}
