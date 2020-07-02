package com.example.civilservantapp.view.activity.refund

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.refund.DeductData
import com.example.civilservantapp.model.refund.Refund
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_check_refund.*
import java.sql.Ref

class CheckRefundActivity : AppCompatActivity() {
    val refundModel : FetchDataViewModel by lazy{
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    lateinit var claim : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var showDate : String
    lateinit var loan : String
    lateinit var loaninterest : String
    lateinit var premium : String
    lateinit var persistent_interest : String
    lateinit var  contract_fines : String
    lateinit var  contract_copy : String
    lateinit var  lost_contract_stamp : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_refund)
        nestedScrollView.visibility = View.GONE
        val policy = intent.getStringExtra("policy")
        val date = intent.getStringExtra("date")
        val lost = intent.getIntExtra("lost",1)
        showDate = intent.getStringExtra("showdate")!!
        Log.d("test",lost.toString())
        check_refund_tool.title = "အမ်းငွေ"
        check_refund_tool.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        setSupportActionBar(check_refund_tool)
        check_refund_tool.setNavigationOnClickListener {
            finish()
        }


        customProgressBar = CustomProgressBar()

        refund_btn.setOnClickListener {
            val intent = Intent(this,
                DoReFundActivity::class.java)
            intent.putExtra("date",date)
            intent.putExtra("claim",claim)
            intent.putExtra("showdate",showDate)
            intent.putExtra("loan",loan)
            intent.putExtra("loaninterest",loaninterest)
            intent.putExtra("premium",premium)
            intent.putExtra("persistance",persistent_interest)
            intent.putExtra("contact_fine",contract_fines)
            intent.putExtra("contract_copy",contract_copy)
            intent.putExtra("lost",lost_contract_stamp)
            startActivity(intent)
            finish()
        }

        refundModel.checkReund(policy!!,date!!,"$lost")
        refundModel.checkRefund.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                   customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Success->{
                    if(it.item.message.equals("fail")){
                        refund_btn.visibility = View.GONE
                        Log.d("test","fail")
                        nestedScrollView.visibility = View.GONE
                        txt_error_message.visibility = View.VISIBLE
                        txt_error_message.text = it.item.data.toString()
                    }
                    else if(it.item.message.equals("success")){
                        Log.d("test","success")
                        refund_btn.visibility = View.VISIBLE
                      if(it.item.data is Refund.Data){

                          txt_error_message.visibility = View.GONE
                          nestedScrollView.visibility = View.VISIBLE
                          val information = it.item.data as Refund.Data
                          val deduct = it.item.deductData as DeductData
                          die_name.setText(information.name)
                          edt_own_id.setText("${information.personalNo}")
                          refund_user_id.setText("${information.id}")
                          refund_age.setText("${information.age}")
                          die_policy.setText(information.policyId)
                          die_nrc.setText(information.nrc)
                          die_amount.setText("${information.amount} ကျပ်")
                          die_period.setText("${information.insurancePeroid} နှစ်")
                          die_state_date.setText(information.startDate)
                          die_end_date.setText(information.endDate)
                          die_period_month.setText("${it.item.paidMonth} လ")
                          die_deposite_amount.setText("${it.item.depositedAmt} ကျပ်")
                          die_claim_amount.setText("${it.item.claimAmount} ကျပ်")
                          claim = "${it.item.claimAmount}"

                          loan = "${deduct.loan}"
                          loaninterest = "${deduct.loanInterest}"
                          premium = "${deduct.premium}"
                          persistent_interest = "${deduct.persistentInterest}"
                          contract_fines = "${deduct.contractFines}"
                          contract_copy = "${deduct.contractCopy}"
                          lost_contract_stamp = "${deduct.lostContractStamp}"



                          edt_loan_money.setText("${deduct.loan} ကျပ်")
                          edt_loan_interest.setText("${deduct.loanInterest} ကျပ်")
                          edt_premium.setText("${deduct.premium} ကျပ်")
                          edt_persistent_interest.setText("${deduct.persistentInterest} ကျပ်")
                          edt_lost_contract_stamp.setText("${deduct.lostContractStamp} ကျပ်")
                          edt_contract_fines.setText("${deduct.contractFines} ကျပ်")
                          edt_contract_copy.setText("${deduct.contractCopy} ကျပ်")
                          edt_total_deduct_amount.setText("${deduct.totalDeductAmount} ကျပ်")
                      }
                    }
                    Log.d("test","success only")
                  customProgressBar.dialog.dismiss()

                }
                is NetWorkViewState.Error->{
                    customProgressBar.dialog.dismiss()
                    refund_btn.visibility = View.GONE
                    txt_error_message.text = it.errormessage.localizedMessage
                    txt_error_message.setTextColor(Color.BLUE)
                    Log.d("test",it.errormessage.localizedMessage!!)
                }

            }
        })


    }
}
