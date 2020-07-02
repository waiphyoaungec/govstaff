package com.example.civilservantapp.phone_check

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.otp.ConfirmApiInterface
import com.example.civilservantapp.otp_pojo.OtpConfirm
import com.example.civilservantapp.view.LoginActivity
import com.example.civilservantapp.view.RegisterActivity
import com.example.civilservantapp.view.activity.ExistingPolicyId
import com.example.civilservantapp.view.activity.ResetPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_confirm.*
import me.myatminsoe.mdetect.MDetect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class ConfirmActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var context = this
    private  lateinit var customProgressBar: CustomProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        var s = intent.getStringExtra("parent")
        var context = this
        MDetect.init(this)
        auth = Firebase.auth
        customProgressBar = CustomProgressBar()
        if(MDetect.isUnicode()) {
            confirm_activity.title = "အတည်ပြုရန်"
            phone_otp.text = "SMS ပေးပို့သော code အားရိုက်ထည့်ရန်"
        }
        else {
            confirm_activity.title = "အတည္ျပဳရန္"
            phone_otp.text = "SMS ေပးပို႔ေသာ code အား႐ိုက္ထည့္ရန္"
        }

        confirm_activity.setNavigationIcon(R.drawable.back_arrow)
        confirm_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        var phone  = intent.getStringExtra("phone")
        var code = intent.getStringExtra("id")
        Log.d("test","code is $code")
        check_otp.setOnClickListener {
            if (edt_otp.text.toString().isNotEmpty()) {
                customProgressBar.show(context,"Loading..")


            }
            else{
                Toast.makeText(context,"Please Enter Completely",Toast.LENGTH_SHORT).show()
            }
        }



//        var retrofit = Retrofit.Builder()
//            .baseUrl("https://solutionshubservice.com/otpservice/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

//        val otpinterface = retrofit.create(ConfirmApiInterface::class.java)
//        check_otp.setOnClickListener {
//            if (edt_otp.text.toString().isNotEmpty()) {
//                val dialog = ProgressDialog(this)
//                dialog.setCancelable(false)
//                dialog.setTitle("Loading")
//                dialog.setMessage("Please Wait..")
//                dialog.show()
//                val call = otpinterface.confirm(
//                    edt_otp.text.toString().trim(),
//                    phone
//                )
//                call.enqueue(object : Callback<OtpConfirm> {
//                    override fun onFailure(call: Call<OtpConfirm>, t: Throwable) {
//                        dialog.dismiss()
//                    }
//
//                    override fun onResponse(
//                        call: Call<OtpConfirm>,
//                        response: Response<OtpConfirm>
//                    ) {
//                        if (response.isSuccessful && response.body() != null) {
//                            if (response.body()!!.errorDesc.equals("Success")) {
//                                if (s.equals("register")) {
//                                    dialog.dismiss()
//                                    val intent = Intent(context,RegisterActivity::class.java)
//                                    intent.putExtra("phone",phone)
//                                    startActivity(intent)
//                                    finish()
//                                } else if (s.equals("reset")) {
//                                    dialog.dismiss()
//                                    var intent = Intent(
//                                        applicationContext,
//                                        ResetPasswordActivity::class.java
//                                    )
//                                    intent.putExtra("phone", phone)
//                                    startActivity(intent)
//                                    finish()
//                                }
//                            } else if (response.body()!!.errorDesc.equals("Incorrect Parameter received")) {
//                                dialog.dismiss()
//                                Toast.makeText(
//                                    applicationContext,
//                                    "ကုတ်မှားနေပါသည် ပြန်လည်ကြိုးစားပါ",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                dialog.dismiss()
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Something wrong " + response.body()!!.errorDesc,
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//                    }
//
//                })
//            }
//            else{
//                Toast.makeText(applicationContext,"Please Enter Code",Toast.LENGTH_SHORT).show()
//            }
//        }

    }

}
