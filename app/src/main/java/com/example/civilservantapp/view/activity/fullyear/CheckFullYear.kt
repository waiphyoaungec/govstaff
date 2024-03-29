package com.example.civilservantapp.view.activity.rest

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.download_extension.DownloadPdf
import com.example.civilservantapp.download_extension.ShowAlert
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.fullyear.FullModel
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_check_full_year.*
import kotlinx.android.synthetic.main.fragment_fullyear.*


class CheckFullYear : AppCompatActivity() {
    val fetchDataViewModel : FetchDataViewModel by lazy{
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    var context = this
    lateinit var id : String
    lateinit var lost : String
    lateinit var user_id : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var amount : String

    lateinit var loan : String
    lateinit var loaninterest : String
    lateinit var premium : String
    lateinit var persistance : String
    lateinit var contractFine : String
    lateinit var contractcopy : String
    lateinit var lostcontractStamp : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_fullyear)

        customProgressBar = CustomProgressBar()
        Log.d("test","fullyear")
        check_rest.setOnClickListener {
            fetchDataViewModel.sendFull(user_id,amount,loan,loaninterest,premium,persistance,contractFine,contractcopy,
            lostcontractStamp)
        }
        val sharedPreference = SharedPreference(context)
        id  = intent.getStringExtra("id")!!
        lost = intent.getStringExtra("lost")!!

        user_id = sharedPreference.getUserInfo("user_id")!!
        fetchDataViewModel.checkFullYear(id,lost)

        fetchDataViewModel.fullcheck.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                    Log.d("test","Loading..")
                }
                is NetWorkViewState.Success->{
                    customProgressBar.dialog.dismiss()
                    if(it.item.data is FullModel.Data) {

                        nestedfullyear.visibility = View.VISIBLE
                        check_rest.visibility = View.VISIBLE
                        val info = it.item.data as FullModel.Data
                        Log.d("test","successfully ${info.age} ${info.name} ${info.amount}")
                        die_name.setText(info.name)
                        die_age.setText("${info.age} နှစ်")
                        die_policy.setText(info.policyId)
                        die_amount.setText("${info.insuranceAmount} ကျပ်")
                        die_period.setText("${info.insurancePeroid} နှစ်")
                        die_state_date.setText(info.startDate)
                        die_end_date.setText(info.endDate)
                        die_period_month.setText("${it.item.paidMonth} လ")
                        die_deposite_amount.setText("${it.item.depositedAmt} ကျပ်")
                        die_claim_amount.setText("${it.item.claimAmount} ကျပ်")
                        amount = it.item.claimAmount.toString()

                        edt_id.setText("${info.id}")
                        edt_fullyear_nrc.setText("${info.nrc}")
                        edt_fullyear_own_id.setText("${info.personalNo}")
                        full_year_soldier.setText("${info.army_status}")
                        edt_fullyear_last_premium.setText("${info.monthlyDate}")
                        edt_fullyear_last_date.setText("${info.dueDate}")

                        val deduct = it.item.deductData;
                        edt_fullloan_money.setText("${deduct.loan} ကျပ်")
                        edt_fullloan_interest.setText("${deduct.loanInterest} ကျပ်")
                        edt_full_premium.setText("${deduct.premium} ကျပ်")
                        edt_full_persistent_interest.setText("${deduct.persistentInterest} ကျပ်")
                        edt_lost_full_contract_stamp.setText("${deduct.lostContractStamp} ကျပ်")
                        edt_full_contract_fines.setText("${deduct.contractFines} ကျပ်")
                        edt_full_contract_copy.setText("${deduct.contractCopy} ကျပ်")
                        edt_full_total_deduct_amount.setText("${deduct.totalDeductAmount} ကျပ်")

                        loan = "${deduct.loan}"
                        loaninterest = "${deduct.loanInterest}"
                        premium = "${deduct.premium}"
                        persistance = "${deduct.persistentInterest}"
                        lostcontractStamp = "${deduct.lostContractStamp}"
                        contractFine = "${deduct.contractFines}"
                        contractcopy = "${deduct.contractCopy}"




                    }
                    else if(it.item.data is String){
                        Log.d("test","is string")
                        check_rest.visibility = View.GONE
                        not_successful.visibility = View.VISIBLE
                        nestedfullyear.visibility = View.GONE
                        rest_status.setText(it.item.data.toString())
                        err_paidmonth.setText("${it.item.paidMonth} လ")
                        err_deposite_amt.setText("${it.item.depositedAmt} ကျပ်")
                        err_claim_amt.setText("${it.item.claimAmount} ကျပ်")
                    }
                }
                is NetWorkViewState.Error->{
                    customProgressBar.dialog.dismiss()
                   fullyear_error.text = "Error Try Again"

                    not_successful.visibility = View.GONE
                    err_paidmonth.setText(it.errormessage.localizedMessage)
                    check_rest.visibility = View.GONE
                    nestedfullyear.visibility = View.GONE
                    Log.d("test",it.errormessage.localizedMessage!!)
                }
            }
        })
        fetchDataViewModel.sendfull.observe(this, Observer {
            when(it){
                is NetWorkViewState.Success->{
                    val savePdf = DownloadPdf(context)
                    Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
                    customProgressBar.dialog.dismiss()
                    if(Build.VERSION.SDK_INT >= 29){
                        val uri = savePdf.downloadQ("fullyear.pdf",it.item.message.data[0])
                        ShowAlert(context).showQ(uri,this)


                    }
                    else if(Build.VERSION.SDK_INT in 23..28){
                        val uri = savePdf.downLoadMiddle("fullyear.pdf",it.item.message.data[0])
                        ShowAlert(context).show(uri,this)

                    }
                    else{
                        val uri = savePdf.downloadLower("fullyear.pdf",it.item.message.data[0])
                        ShowAlert(context).show(uri,this)

                    }
                }
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Error->{
                    not_successful.visibility = View.GONE
                    check_rest.visibility = View.GONE
                    fullyear_error.setText("Error Please Try Again")
                    customProgressBar.dialog.dismiss()
                }
            }
        })

    }

}
