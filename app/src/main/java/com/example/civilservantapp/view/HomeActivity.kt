package com.example.civilservantapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.fragment.FlifeInsurance_Fragment

import kotlinx.android.synthetic.main.activity_home.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class HomeActivity : AppCompatActivity(), View.OnClickListener {
lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        MDetect.init(this)
        sharedPreference = SharedPreference(this)

        life_form_layout.setOnClickListener(this)
        logout_layout.setOnClickListener(this)
        profile_img.setOnClickListener(this)
        if(MDetect.isUnicode()){
            lbllifeform.setText(Rabbit.zg2uni(getString(R.string.lifeTitle)))
            toolbar.setTitle(Rabbit.zg2uni(getString(R.string.home_appname)))
            profile_name.text = sharedPreference.getUserInfo("name")
            profile_phno.text = sharedPreference.getUserInfo("phone")
        }else{
            lbllifeform.setText(getString(R.string.lifeTitle))
            toolbar.setTitle(getString(R.string.home_appname))
            profile_name.text = sharedPreference.getUserInfo("name")
            profile_phno.text = sharedPreference.getUserInfo("phone")
        }
        setSupportActionBar(toolbar)
    }


    override fun onClick(view: View?) {
        if(view==life_form_layout){
            val insuranceForm = Intent(this, FlifeInsurance_Fragment::class.java)
            startActivity(insuranceForm)
        }else if(view==profile_img){
            val insuranceForm = Intent(this, ProfileActivity::class.java)
            startActivity(insuranceForm)
        }else if(view==logout_layout){
            sharedPreference.saveUserInfo("userid", "")
            sharedPreference.saveUserInfo("name", "")
            sharedPreference.saveUserInfo("username", "")
            sharedPreference.saveUserInfo("email", "")
            sharedPreference.saveUserInfo("phone", "")
            sharedPreference.saveUserInfo("token", "")
            sharedPreference.saveUserInfo("policy_id", "")
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(loginIntent)
            finish()
        }
    }

}
