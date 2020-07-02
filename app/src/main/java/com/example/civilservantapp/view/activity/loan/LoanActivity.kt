package com.example.civilservantapp.view.activity.loan

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.loan.Loan
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_check_refund.*
import kotlinx.android.synthetic.main.activity_get_contract.*
import kotlinx.android.synthetic.main.activity_loan.*
import kotlinx.android.synthetic.main.fragment_loan.*
import kotlinx.android.synthetic.main.fragment_loan.check_refund_tool
import kotlinx.android.synthetic.main.fragment_loan.constraint_firstform
import kotlinx.android.synthetic.main.fragment_loan.die_amount
import kotlinx.android.synthetic.main.fragment_loan.die_claim_amount
import kotlinx.android.synthetic.main.fragment_loan.die_deposite_amount
import kotlinx.android.synthetic.main.fragment_loan.die_end_date
import kotlinx.android.synthetic.main.fragment_loan.die_name
import kotlinx.android.synthetic.main.fragment_loan.die_nrc
import kotlinx.android.synthetic.main.fragment_loan.die_period
import kotlinx.android.synthetic.main.fragment_loan.die_period_month
import kotlinx.android.synthetic.main.fragment_loan.die_policy
import kotlinx.android.synthetic.main.fragment_loan.die_state_date
import kotlinx.android.synthetic.main.fragment_loan.nestedScrollView
import kotlinx.android.synthetic.main.fragment_loan.refund_btn
import kotlinx.android.synthetic.main.fragment_loan.txt_error_message

class LoanActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    lateinit var person_id : String
    lateinit var customProgressBar: CustomProgressBar
    lateinit var loanamt : String
    lateinit var ctx : Context
    val fetDataViewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_loan)
        customProgressBar = CustomProgressBar()
        ctx = applicationContext
        txt_error_message.visibility = View.GONE
        nestedScrollView.visibility = View.GONE
        refund_btn.visibility = View.GONE

        val sharedPreference = SharedPreference(ctx)
        person_id = sharedPreference.getUserInfo("user_id")!!
        val policy_id  = intent.getStringExtra("id")!!

        check_refund_tool.title = "ချေးငွေ"
        check_refund_tool.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        setSupportActionBar(check_refund_tool)
        check_refund_tool.setNavigationOnClickListener {
            finish()
        }

        fetDataViewModel.checkLoan(policy_id)
        refund_btn.setOnClickListener {
            val intent = Intent(applicationContext, SendLoyanActivity::class.java)
            intent.putExtra("id",person_id)
            intent.putExtra("policy_id",policy_id)
            intent.putExtra("loanamt",loanamt)
            finish()
            startActivity(intent)
        }

        fetDataViewModel.checkLoan.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    Log.d("testtest","Loading")
                    customProgressBar.show(this,"Loading....")
                }
                is NetWorkViewState.Success->{

                    Log.d("testtest","success ${it.item.data}")
                    if(it.item.data is String){
                        txt_error_message.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.GONE
                        txt_error_message.text = "${it.item.data}"
                        customProgressBar.dialog.dismiss()
                        Toast.makeText(ctx,"မအောင်မြင်ပါ...", Toast.LENGTH_SHORT).show()

                    }
                    else if(it.item.data is Loan.Data){
                        var army = ""
                        customProgressBar.dialog.dismiss()
                        nestedScrollView.visibility = View.VISIBLE
                        refund_btn.visibility = View.VISIBLE
                        constraint_firstform.visibility = View.VISIBLE
                        Toast.makeText(ctx,"ငွေချေးနိုင်ပါသည်", Toast.LENGTH_SHORT).show()
                        val infomations = it.item.data as Loan.Data
                        die_name.setText(infomations.name)
                        die_policy.setText(infomations.policyId)
                        die_nrc.setText(infomations.nrc)
                        if(infomations.army == "1"){
                            army = "တပ်မတော်သား"
                        }
                        else{
                            army = "အခြား"
                        }

                        edt_milltray.setText(army)
                        die_amount.setText("${infomations.insuranceAmount} ကျပ်")
                        die_id.setText("${infomations.id}")
                        edt_age.setText("${infomations.age}")
                        edt_own_name.setText("${infomations.personalNo}")
                        die_period.setText("${infomations.insurancePeroid} နှစ်")
                        die_state_date.setText(infomations.startDate)
                        edt_last_premium.setText("${infomations.monthlyDate}")
                        die_end_date.setText(infomations.endDate)
                        edt_end_time.setText("${infomations.dueDate}")
                        die_period_month.setText("${it.item.paidMonth} လ")
                        die_deposite_amount.setText("${it.item.depositedAmt} ကျပ်")
                        die_claim_amount.setText("${it.item.loanamt} ကျပ်")
                        edt_stamp.setText("${it.item.stamp}")

                        loanamt = "${it.item.loanamt}"




                    }
                }
                is NetWorkViewState.Error->{
                    Log.d("testtest",it.errormessage.localizedMessage!!)
                    txt_error_message.visibility = View.VISIBLE
                    nestedScrollView.visibility = View.GONE
                    customProgressBar.dialog.dismiss()
                    txt_error_message.text = "Error Please Try Again"
                    Toast.makeText(ctx,"Somethings Wrong", Toast.LENGTH_SHORT).show()
                }
            }

        })




    }
}
