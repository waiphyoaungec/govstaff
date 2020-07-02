package com.example.civilservantapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.activity.loan.LoanActivity
import com.example.civilservantapp.view.activity.rest.CheckFullYear
import com.example.civilservantapp.view.activity.rest.CheckRest
import kotlinx.android.synthetic.main.activity_check_policy.*

class CheckPolicy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_policy)

        val sharedPreference = SharedPreference(this)
        val policy_id  = sharedPreference.getUserInfo("policy_id")!!

        choose_policy_id.setText(policy_id)

        val title = intent.getStringExtra("title")!!
        Log.d("test",title)
        choose_tool_bar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        if(title.equals("loan")){
            choose_tool_bar.title = "ချေးငွေလျှောက်ရန်"
        }
        else if(title.equals("close")){
            choose_tool_bar.title = "စာရင်းပိတ်လျှောက်ထားရန်"
        }
        else if(title.equals("fullyear")){
            choose_tool_bar.title = "နှစ်စေ့တောင်းခံလွှာလျှောက်ထားရန်"
        }

        setSupportActionBar(choose_tool_bar)
        choose_tool_bar.setNavigationOnClickListener {
            finish()
        }
        choose_id.setOnClickListener {
            if(choose_policy_id.text.isNullOrEmpty()){
                Toast.makeText(applicationContext,"Please Fill Policy Id",Toast.LENGTH_SHORT).show()
            }
            else{
                if(title.equals("loan")) {
                    val i = Intent(this, LoanActivity::class.java)
                    i.putExtra("id",choose_policy_id.text.toString())
                    startActivity(i)
                }
                else if(title.equals("close")){
                    val i = Intent(this, CheckRest::class.java)
                    i.putExtra("id",choose_policy_id.text.toString())
                    startActivity(i)
                }
                else if(title.equals("fullyear")){
                    val i = Intent(this, CheckFullYear::class.java)
                    i.putExtra("id",choose_policy_id.text.toString())
                    startActivity(i)
                }
            }
        }
    }
}
