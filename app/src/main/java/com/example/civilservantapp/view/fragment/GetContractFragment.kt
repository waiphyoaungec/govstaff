package com.example.civilservantapp.view.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.fragment_get_contract.*

class GetContractFragment : Fragment() {
    private var policyId: String=""
    private var downloadLink: String=""
    lateinit var progressDialog: ProgressDialog
    lateinit var sharedPreference: SharedPreference
    private var policyIdKey: String = "policy_id"
    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_contract, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressDialog = ProgressDialog(activity)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage("စစ်ဆေးနေပါသည်")
        progressDialog.setCancelable(false)
        sharedPreference = SharedPreference(activity!!)
        edt_policyid.setText(getpolicyNo())
        fetchDataViewModel.contractData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.download_link!!.url==null){
                        btndownload_policyid.visibility=View.GONE
                        Toast.makeText(activity, "ပေါ်လစီစာချုပ် မရှိသေးပါ။", Toast.LENGTH_SHORT).show()
                    }else{
                        downloadLink = it.item.path+it.item.download_link!!.url
                        btndownload_policyid.visibility = View.VISIBLE
                        Log.d("GetContract", downloadLink)
                    }

                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        btncheck_policyid.setOnClickListener {
            policyId = edt_policyid.text.toString()

            if(policyId!=""){
                fetchDataViewModel.getContract(policyId)
            }
        }
        btndownload_policyid.setOnClickListener {
            downloadPDF(downloadLink)
        }
    }
    fun downloadPDF(pdflink: String){
        val pdfdownload = Intent(Intent.ACTION_VIEW)
        pdfdownload.setDataAndType(Uri.parse(pdflink), "application/pdf")
        startActivityForResult(pdfdownload, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            progressDialog.dismiss()
            btndownload_policyid.visibility=View.GONE
        }
    }

    fun getpolicyNo():String{
        if(sharedPreference.getUserInfo(policyIdKey)==null){
            Toast.makeText(context,"data များကိုအရင်ဖြည့်ပါ",Toast.LENGTH_SHORT).show()
            return ""
        }else{
            return sharedPreference.getUserInfo(policyIdKey)!!
        }

    }
}
