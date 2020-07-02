package com.example.civilservantapp.view.activity.dead

import android.content.Intent
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
import com.example.civilservantapp.model.dead.Dead
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_dead.*

class CheckDead : AppCompatActivity() {
    lateinit var progressBar: CustomProgressBar
    lateinit var name : String
    var age = 0
    lateinit var deadDate : String
    var ammount  = 0.0
    lateinit var date : String
    lateinit var showDate : String
    lateinit var policyId : String
    
    val fetchDataViewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead)
        check_dead.visibility = View.GONE
        tool_die.title = "သေဆုံးလျှောက်ရန်"

        date = intent.getStringExtra("date")!!
        policyId = intent.getStringExtra("policy")!!
        showDate = intent.getStringExtra("showdate")!!
        tool_die.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        setSupportActionBar(tool_die)
        tool_die.setNavigationOnClickListener {
            finish()
        }
        check_dead.setOnClickListener {
            val intent = Intent(this,
                SendDead::class.java)
            finish()
            intent.putExtra("name",name)
            intent.putExtra("age",age)
            intent.putExtra("deadDate",deadDate)
            intent.putExtra("ammount",ammount)
            intent.putExtra("date",date)
            intent.putExtra("showdate",showDate)
            intent.putExtra("policy",policyId)
            startActivity(intent)


        }
        progressBar = CustomProgressBar()
        fetchDataViewModel.checkDead(policyId,date)


        fetchDataViewModel.checkDead.observe(this, Observer {
           when(it){
               is NetWorkViewState.Loading->{
                   Log.d("test","Loading...")
                   progressBar.show(this,"Loading..")
               }
               is NetWorkViewState.Success->{
                   Log.d("test","success")
                   progressBar.dialog.dismiss()
                   if((it.item.data is Dead.Data)){
                       nestedScrollView.visibility = View.VISIBLE
                       check_dead.visibility = View.VISIBLE
                       val data = (it.item.data as Dead.Data)
                       die_name.setText((data.name))
                       die_policy.setText(data.policyId)
                       die_nrc.setText(data.nrc)
                       die_amount.setText("${data.insuranceAmount} ကျပ်")
                       die_period.setText("${data.insurancePeroid} လ")
                       die_state_date.setText(data.startDate)
                       die_end_date.setText(data.endDate)
                       die_period_month.setText("${it.item.paidMonth} လ")
                       die_deposite_amount.setText("${it.item.depositedAmt} ကျပ်")
                       die_claim_amount.setText("${it.item.claimAmount} ကျပ်")
                       die_age.setText("${data.age} နှစ်")
                       name = data.name
                       age = data.age
                       ammount = it.item.claimAmount
                       deadDate = "2020-01-16"

                   }
                   else{
                       check_dead.visibility = View.GONE
                       nestedScrollView.visibility = View.GONE
                       Log.d("test","${it.item.data}")
                       die_status.visibility = View.VISIBLE
                       die_status.text = it.item.data.toString()
                   }

               }
               is NetWorkViewState.Error->{
                   Log.d("test","error")
                   nestedScrollView.visibility = View.GONE
                   check_dead.visibility = View.GONE
                   die_status.text = "မအောင်မြင်ပါ....."
                   progressBar.dialog.dismiss()
               }

        }

        })
    }
}
