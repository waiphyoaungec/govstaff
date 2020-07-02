package com.example.civilservantapp.view

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.phone_check.PhoneRegister
import com.example.civilservantapp.view.activity.ParentHomeActivity
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class LoginActivity : AppCompatActivity(), View.OnClickListener {
private val lifeInsuranceViewModel : LifeInsuranceViewModel
        by lazy { ViewModelProvider(this).get(LifeInsuranceViewModel::class.java) }

    lateinit var customProgressBar : CustomProgressBar
    lateinit var typeFace : Typeface
    var phone: String?=""
    var password: String?=""
    lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MDetect.init(this)
        typeFace = Typeface.createFromAsset(assets,"pyidaungsu.ttf")
        customProgressBar = CustomProgressBar()

        if(MDetect.isUnicode()) {
            txtRegister.text = HtmlCompat.fromHtml("<u>မှတ်တမ်းတင်ရန်</u>",HtmlCompat.FROM_HTML_MODE_LEGACY)
            txtReset.text = HtmlCompat.fromHtml("<u>စကားဝှက်ပြန်လျှောက်ရန်</u>",HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        else{
            txtRegister.text = HtmlCompat.fromHtml("<u>မွတ္တမ္းတင္ရန္</u>",HtmlCompat.FROM_HTML_MODE_LEGACY)
            txtReset.text = HtmlCompat.fromHtml("<u>စကားဝွက္ျပန္ေလွ်ာက္ရန္</u>",HtmlCompat.FROM_HTML_MODE_LEGACY)
            txtlogintitle.text = Rabbit.uni2zg(getString(R.string.title_login))
            edt_loginpassword.hint = Rabbit.uni2zg(getString(R.string.hint_rg_password))
            btnLogin.text = Rabbit.uni2zg(getString(R.string.btn_login))

        }


        btnLogin.setOnClickListener(this)
        txtRegister.setOnClickListener(this)
        txtReset.setOnClickListener(this)
        sharedPreference = SharedPreference(this)
        lifeInsuranceViewModel.loginData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Success->{

                    if(it.item.message.validation_fail!=null){
                        Toast.makeText(this, it.item.message.validation_fail, Toast.LENGTH_SHORT).show()
                        customProgressBar.dialog.dismiss()
                    }else{
                        sharedPreference.saveUserInfo("user_id", it.item.message.id!!.toString())
                        sharedPreference.saveUserInfo("name", it.item.message.name!!)
                        sharedPreference.saveUserInfo("username", it.item.message.username!!)
                        if(it.item.message.email==null){
                            sharedPreference.saveUserInfo("email", "")
                        }else{
                            sharedPreference.saveUserInfo("email", it.item.message.email!!)
                        }

                        sharedPreference.saveUserInfo("phone", it.item.message.phone!!)
                        sharedPreference.saveUserInfo("token", it.item.message.token!!)
                        if(it.item.message.policy_id==null){
                            sharedPreference.saveUserInfo("policy_id", "")
                        }else{
                            sharedPreference.saveUserInfo("policy_id", it.item.message.policy_id!!)
                        }

                        customProgressBar.dialog.dismiss()
                        val homeIntent = Intent(this, ParentHomeActivity::class.java)
                        startActivity(homeIntent)
                        finish()
                    }
                }
                is NetWorkViewState.Error->{
                    customProgressBar.dialog.dismiss()
                    Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်မှုမအောင်မြင်ပါ", Toast.LENGTH_SHORT).show()
                }
            }
        })
        if(sharedPreference.getUserInfo("user_id")!=null){
            if(sharedPreference.getUserInfo("user_id")!=""){
                Log.d("Login", "token"+sharedPreference.getUserInfo("token")+"")
                val homeIntent = Intent(this, ParentHomeActivity::class.java)
                startActivity(homeIntent)
                finish()
            }
        }else{

        }

    }

    override fun onClick(view: View?) {
        if(view==btnLogin){
            if(check_networkconnection()){
                phone = "09"+edt_loginphone.text.toString()
                password = edt_loginpassword.text.toString()
                if(phone!="" && password!=""){
                    lifeInsuranceViewModel.LoginUser(phone!!, password!!)

                }else{
                    Toast.makeText(applicationContext,"Please Enter Completely",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }

        } else if(view==txtRegister){
            if(check_networkconnection()){
                val intent = Intent(this, PhoneRegister::class.java)
                intent.putExtra("register","register")
                startActivity(intent)
            }else{
                Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }

        }else if(view==txtReset){
            if(check_networkconnection()){
                val intent = Intent(this, PhoneRegister::class.java)
                intent.putExtra("register","reset")
                startActivity(intent)
            }else{
                Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun check_networkconnection(): Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}
