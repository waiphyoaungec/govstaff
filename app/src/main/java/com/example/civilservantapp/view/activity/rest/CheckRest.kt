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
import com.example.civilservantapp.model.rest.RestModel
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_check_rest.*
import kotlinx.android.synthetic.main.fragment_closed.*

class CheckRest : AppCompatActivity() {
    val fetchDataViewModel : FetchDataViewModel by lazy{
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    lateinit var id : String
    lateinit var policy_id : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var amount : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_closed)

        customProgressBar = CustomProgressBar()

        check_rest.setOnClickListener {
            fetchDataViewModel.sendRest(id,amount)
        }
        val sharedPreference = SharedPreference(applicationContext)
        id  = sharedPreference.getUserInfo("user_id")!!
        policy_id =  intent.getStringExtra("id")!!

        fetchDataViewModel.checkRest(policy_id)

        fetchDataViewModel.checkRest.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                    Log.d("test","Loading..")
                }
                is NetWorkViewState.Success->{
                    customProgressBar.dialog.dismiss()
                    if(it.item.data is RestModel.Data) {
                        Log.d("test","successfully Closed")
                        nestedScrollView.visibility = View.VISIBLE
                        check_rest.visibility = View.VISIBLE
                        val info = it.item.data as RestModel.Data
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
                    }
                    else if(it.item.data is String){
                        Log.d("test","is string")
                        check_rest.visibility = View.GONE
                        not_successful.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.GONE
                        rest_status.setText(it.item.data.toString())
                        err_paidmonth.setText("${it.item.paidMonth} လ")
                        err_deposite_amt.setText("${it.item.depositedAmt} ကျပ်")
                        err_claim_amt.setText("${it.item.claimAmount} ကျပ်")
                    }
                }
                is NetWorkViewState.Error->{
                    customProgressBar.dialog.dismiss()
                    not_successful.visibility = View.GONE
                    close_error.text = "Error Please Try Again"
                    err_paidmonth.setText(it.errormessage.localizedMessage)
                    check_rest.visibility = View.GONE
                    nestedScrollView.visibility = View.GONE
                    Log.d("test",it.errormessage.localizedMessage!!)
                }
            }
        })
        fetchDataViewModel.sendRest.observe(this, Observer {
            when(it){
                is NetWorkViewState.Success->{
                    val savePdf = DownloadPdf(applicationContext!!)
                    Toast.makeText(applicationContext,"success", Toast.LENGTH_SHORT).show()
                    customProgressBar.dialog.dismiss()
                    if(Build.VERSION.SDK_INT >= 29){
                        val uri = savePdf.downloadQ("close.pdf",it.item.message.data[0])
                        ShowAlert(this).showQ(uri,this)


                    }
                    else if(Build.VERSION.SDK_INT in 23..28){
                        val uri = savePdf.downLoadMiddle("close.pdf",it.item.message.data[0])
                        ShowAlert(this).show(uri,this)

                    }
                    else{
                        val uri = savePdf.downloadLower("close.pdf",it.item.message.data[0])
                        ShowAlert(this).show(uri,this)

                    }
                }
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Error->{
                    not_successful.visibility = View.VISIBLE
                    check_rest.visibility = View.GONE
                    Toast.makeText(applicationContext,"မအောင်မြင်ပါ", Toast.LENGTH_SHORT).show()
                    customProgressBar.dialog.dismiss()
                }
            }
        })
    }

}
