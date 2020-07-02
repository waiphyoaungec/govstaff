package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.downloader.*
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.payment.MonthyFeeResponse
import com.example.civilservantapp.view.ConfirmPaymentActivity
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_monthly_pay.*
import java.text.SimpleDateFormat

class MonthlyPayActivity : AppCompatActivity() {
    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }
    private var insurance_person_id: Int=0
    private var policyid: String=""
    lateinit var progressDialog: ProgressDialog
    private var name: String=""
    private var nrc: String=""
    private var insuranceamt: Int=0
    private var insuranceperiod: String=""
    private var paidcount: Int = 0
    private var paidList: ArrayList<String> = arrayListOf()
    private var overduecount: Int = 0
    private var overdueList : ArrayList<String> = arrayListOf()
    private var monthlyAmt: Int =0
    private var totalDepositAmt: Int = 0

    private var monthlyDate: String=""
    private var dueDate: String=""

    private var bank: Int = 0
    private var easypay: Int = 0
    private var sh: Int = 0
    private var officeId: Int=0
    private var msg: String=""
    private var txtpaidmonth :String=""
    private var txtoverduemonth: String=""
    private var txtcountpaidmonth: String=""
    private var txtcountduemonth: String = ""
    private var paymentType: String = ""

    lateinit var sharedPreference: SharedPreference
    private var policyIdKey: String = "policy_id"
    private var invoiceId: String?=""
    private var submitedmonth: String?=""
    private var amount: Int?=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_pay)
        monthlypay_toolbar.setTitle("လစဉ်ကြေးပေးရန်")
        monthlypay_toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        setSupportActionBar(monthlypay_toolbar)
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        sharedPreference = SharedPreference(this)
        edt_policyid_monthlypay.setText(getpolicyNo())
        btncheck_policyid_payment.setOnClickListener {
            policyid = edt_policyid_monthlypay.text.toString()
            if(policyid!=""){
                fetchDataViewModel.getMonthlyFee(policyid)
            }
        }
        btncontinuepayment.setOnClickListener {
            if(rdbtnotc.isChecked){
                paymentType = "otc"
            }else if(rdbtnmwp.isChecked){
                paymentType = "easypay"
            }
            fetchDataViewModel.postMonthlyFee(paymentType, insurance_person_id, officeId, nrc, easypay, bank, sh, monthlyDate,
                dueDate, monthlyAmt)
        }
        fetchDataViewModel.postmonthlyFeeData.observe(this, Observer {
            when(it)
            {
                is NetWorkViewState.Loading->{
                    progressDialog.setMessage("လုပ်ဆောင်နေပါသည်")
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.insurance_person_id!=null){
                        Log.d("PostMonthlyPay", Gson().toJson(it.item)+"\n"+it.item.insurance_person_id+"\n"+insurance_person_id)
                        invoiceId = it.item.invoice_id
                        amount = it.item.amount
                        for(i in 0..paidList.size-1){
                            submitedmonth+=paidList.get(i)+" - "
                        }
                        val otcpayIntent = Intent(this, ConfirmPaymentActivity::class.java)
                        with(otcpayIntent){
                            putExtra("insurance_person_id", insurance_person_id)
                            putExtra("name", name)
                            putExtra("policyid", policyid)
                            putExtra("invoiceid", invoiceId)
                            putExtra("submitedmonth", submitedmonth)
                            putExtra("amount", amount)
                            putExtra("paymenttype", paymentType)
                        }
                        startActivity(otcpayIntent)
                        submitedmonth=""
                        //startActivityForResult(otcpayIntent, 1)
                    }else{
                        Toast.makeText(this, it.item.response, Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
        fetchDataViewModel.getmonthlyFeeData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.setMessage("ဒေတာများရယူနေပါသည်")
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.msg=="success"){
                        Log.d("Monthly", Gson().toJson(it.item))
                        bind_personData(it.item)
                    }else if(it.item.msg=="error"){
                        card_paymentinformation.visibility = View.GONE
                        Toast.makeText(this, it.item.response, Toast.LENGTH_SHORT).show()
                    }
                    //clearTextField()
                    hideKeyboard()
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{

                }
            }
        })
        monthlypay_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    fun bind_personData(monthlyFeeResponse: MonthyFeeResponse){
        card_paymentinformation.visibility = View.VISIBLE
        insurance_person_id = monthlyFeeResponse.insurance_person_id!!
        name = monthlyFeeResponse.name!!
        nrc = monthlyFeeResponse.nrc!!
        insuranceamt = monthlyFeeResponse.insurance_amount!!
        insuranceperiod = monthlyFeeResponse.insurance_peroid!!
        paidcount = monthlyFeeResponse.count_paid_month!!
        paidList = monthlyFeeResponse.paid_month!!
        overduecount = monthlyFeeResponse.count_overdue_m!!
        overdueList = monthlyFeeResponse.overdue_month!!
        monthlyDate = monthlyFeeResponse.monthly_date!!
        dueDate = monthlyFeeResponse.due_date!!
        bank = monthlyFeeResponse.bank!!
        easypay = monthlyFeeResponse.easypay!!
        sh = monthlyFeeResponse.sh!!
        monthlyAmt = monthlyFeeResponse.monthly_amount!!
        totalDepositAmt = monthlyFeeResponse.total_deposit_amt!!
        officeId = monthlyFeeResponse.office_id!!
        msg = monthlyFeeResponse.msg!!

        txtmonthlyname.setText(name)
        txtmonthlynrc.setText(nrc)
        txtmonthlyinsuranceamt.setText(insuranceamt.toString() + " ကျပ်")
        txtmonthlyinsuranceperiod.setText(insuranceperiod + " နှစ်")

        txtcountpaidmonth = ""
        txtcountpaidmonth = "အာမခံ သွင်းပြီးသည့်လ" + "(" + monthlyFeeResponse.count_paid_month + " လ)"
        lblmonthlyinsurancepaidmonths.setText(txtcountpaidmonth)
        txtpaidmonth = ""
        for(i in monthlyFeeResponse.paid_month!!){
            txtpaidmonth +=convert_ymd_dmy(i)+"\n"
        }
        txtmonthlyinsurancepaidmonths.setText(txtpaidmonth)
        txtcountduemonth = ""
        txtcountduemonth = "အာမခံ ပေးသွင်းရန်လ" + "(" + monthlyFeeResponse.count_overdue_m + " လ)"
        lblmonthlyneedpaidmonths.setText(txtcountduemonth)
        txtoverduemonth = ""

        for(i in monthlyFeeResponse.overdue_month!!){
            txtoverduemonth +=convert_ymd_dmy(i)+"\n"
        }
        txtmonthlyneedpaidmonths.setText(txtoverduemonth)
        txtmonthlypremiumkyats.setText(monthlyAmt.toString() + " ကျပ်")
        txtmonthlytotalpremiumkyats.setText(totalDepositAmt.toString()+ " ကျပ်")
    }

    fun clearTextField(){
        edt_policyid_monthlypay.setText("")
    }

    fun hideKeyboard(){
        val view = findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getpolicyNo():String{
        if(sharedPreference.getUserInfo(policyIdKey)==null){
            return ""
        }else{
            return sharedPreference.getUserInfo(policyIdKey)!!
        }

    }

    fun downloadPDF(pdflink: String){
        val pdfdownload = Intent(Intent.ACTION_VIEW)
        pdfdownload.setDataAndType(Uri.parse(pdflink), "application/pdf")
        startActivity(pdfdownload)
    }

    fun pr_downloader(pdflink: String){
        val downloadId = PRDownloader.download(pdflink, "", "")
            .build()
            .setOnStartOrResumeListener { }
            .setOnPauseListener { }
            .setOnCancelListener(object : OnCancelListener {
                override fun onCancel() {

                }
            })
            .setOnProgressListener(object : OnProgressListener {
                override fun onProgress(progress: Progress?) {

                }
            })
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {

                }

                override fun onError(error: com.downloader.Error?) {

                }
            })
    }
    fun convert_ymd_dmy(ymd: String): String{
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val sdf2 = SimpleDateFormat("dd-MM-yyyy")
        var df2 = sdf2.format(sdf1.parse(ymd))
        return df2
    }
}
