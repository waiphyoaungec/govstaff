package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_get_contract.*

class GetContractActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_get_contract)
        getcontract_toolbar.setTitle("ပေါ်လစီစာချုပ်ထုတ်ယူရန်")
        getcontract_toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        setSupportActionBar(getcontract_toolbar)
        getcontract_toolbar.setNavigationOnClickListener {
            finish()
        }
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage("စစ်ဆေးနေပါသည်")
        progressDialog.setCancelable(false)
        sharedPreference = SharedPreference(this)
        edt_policyid.setText(getpolicyNo())
        fetchDataViewModel.contractData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.download_link != null){
                        downloadLink = it.item.path+it.item.download_link!!.url
                        btndownload_policyid.visibility = View.VISIBLE
                    }
                    else{
                        Toast.makeText(applicationContext,"Download is null",Toast.LENGTH_SHORT).show()
                    }
                    downloadLink = it.item.path+it.item.download_link!!.url
                    btndownload_policyid.visibility = View.VISIBLE
                    Log.d("GetContract", downloadLink)
                    if(it.item.download_link!!.url==null){
                        btndownload_policyid.visibility=View.GONE
                        Toast.makeText(this, "ပေါ်လစီစာချုပ် မရှိသေးပါ။", Toast.LENGTH_SHORT).show()
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
            return ""
        }else{
            return sharedPreference.getUserInfo(policyIdKey)!!
        }

    }
}
