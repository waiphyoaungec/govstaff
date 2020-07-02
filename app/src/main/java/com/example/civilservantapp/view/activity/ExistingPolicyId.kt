package com.example.civilservantapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.view.LoginActivity
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import kotlinx.android.synthetic.main.activity_existing_policy_id.*


class ExistingPolicyId : AppCompatActivity() {

    val lifeInsuranceViewModel : LifeInsuranceViewModel by lazy{
        ViewModelProvider(this).get(LifeInsuranceViewModel::class.java)
    }
    lateinit var id : String
    lateinit var customProgressBar: CustomProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_existing_policy_id)
        customProgressBar = CustomProgressBar()

        reg_toolbar.title = "ယခင်ပေါ်လစီအိုင်ဒီရှိပါကချိတ်ရန်"
        setSupportActionBar(reg_toolbar)
        reg_toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        reg_toolbar.setNavigationOnClickListener {
            finish()
        }


        val sharedPreference = SharedPreference(this)




        if (sharedPreference.getUserInfo("user_id") != null || sharedPreference.getUserInfo("user_id") != "") {
           id = sharedPreference.getUserInfo("user_id").toString()
        }
        else
        intent.getStringExtra("id").let {

            val img = it
           id = Base64.decode(img, Base64.DEFAULT).toString(charset("UTF-8"))

        }

        lifeInsuranceViewModel.joinPolicyData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Success ->{
                    if(customProgressBar.dialog.isShowing){
                        customProgressBar.dialog.dismiss()
                    }
                   if(it.item.message.equals("true")){
                       Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                       val intent = Intent(this, LoginActivity::class.java)
                       startActivity(intent)
                       finish()

                   }
                    else{
                       Toast.makeText(applicationContext,it.item.message,Toast.LENGTH_SHORT).show()
                   }


                }
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Error->{
                    if(customProgressBar.dialog.isShowing){
                        customProgressBar.dialog.dismiss()
                    }
                    Toast.makeText(applicationContext,"error ${it.errormessage}",Toast.LENGTH_SHORT).show()
                }

            }
        })
        existing_policy_btn.setOnClickListener {
            if(edt_existing_policy.text.toString().isEmpty()){
                Toast.makeText(this,"မှန်ကန်စွာဖြည့်ပေးပါ",Toast.LENGTH_SHORT).show()
            }
            else{
                lifeInsuranceViewModel.joinPolicyId(id,edt_existing_policy.text.toString())
            }
        }





    }
}
