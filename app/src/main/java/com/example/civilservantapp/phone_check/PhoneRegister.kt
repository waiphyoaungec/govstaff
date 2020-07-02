package com.example.civilservantapp.phone_check

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.view.RegisterActivity
import com.example.civilservantapp.view.activity.ResetPasswordActivity
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_phone_register.*
import me.myatminsoe.mdetect.MDetect
import java.util.concurrent.TimeUnit


class PhoneRegister : AppCompatActivity() {
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy {
        ViewModelProvider(this).get(
            LifeInsuranceViewModel::class.java
        )
    }
    lateinit var s: String
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private val context = this
    lateinit var progressDialog: CustomProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_register)
        confirm_layout.visibility = View.GONE
        MDetect.init(this)
        auth = Firebase.auth
        progressDialog = CustomProgressBar()
        s = intent.getStringExtra("register")!!

        if (MDetect.isUnicode())
            phone_register.text = "ဖုန်းနံပါတ်"
        else
            phone_register.text = "ဖုန္းနံပါတ္"

        if (s.equals("register")) {
            if (MDetect.isUnicode())
                reg_toolbar.title = "မှတ်တမ်းတင်ရန်"
            else
                reg_toolbar.title = "မွတ္တမ္းတင္ရန္"
        } else if (s.equals("reset")) {
            if (MDetect.isUnicode())
                reg_toolbar.title = "စကားဝှက်ပြန်လျှောက်ရန်"
            else
                reg_toolbar.title = "စကားဝွက္ျပန္ေလွ်ာက္ရန္"
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("test", "success")
                signInWithPhoneAuthCredential(credential,"+959${edt_registername.text}")


            }

            override fun onVerificationFailed(e: FirebaseException) {
                if(progressDialog.dialog.isShowing){
                    progressDialog.dialog.dismiss()
                }
                Log.d("test", "failure" + e.localizedMessage)
                Toast.makeText(applicationContext, "မအောင်မြင်ပါ....\n" + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(

                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("test","code send" + verificationId)
                storedVerificationId = verificationId
                Toast.makeText(this@PhoneRegister,"success",Toast.LENGTH_SHORT).show()
                if(progressDialog.dialog.isShowing){
                    progressDialog.dialog.dismiss()
                }
                hideSend()
                confirm_layout.visibility = View.VISIBLE

            }

        }


        setSupportActionBar(reg_toolbar)
        reg_toolbar.setNavigationIcon(R.drawable.back_arrow)
        reg_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        register_next.setOnClickListener {
            if (edt_registername.text.toString().trim().isNotEmpty())
                lifeInsuranceViewModel.RegisterUser(
                    "",
                    "",
                    "",
                    "09" + edt_registername.text.toString().trim(),
                    "",
                    ""
                )
            else
                Toast.makeText(
                    applicationContext,
                    "Please Enter Phone Number",
                    Toast.LENGTH_SHORT
                ).show()
        }

        check_otp.setOnClickListener {
            progressDialog.show(this,"Loading..")
            verifyPhoneNumberWithCode(storedVerificationId,edt_otp.text.toString().trim(),"+959${edt_registername.text.toString().trim()}")

        }

        lifeInsuranceViewModel.registData.observe(this, Observer {
            when (it) {
                is NetWorkViewState.Loading -> {
                    progressDialog.show(this, "Loading")
                }
                is NetWorkViewState.Success -> {


                    if (it.item.message.phone != null) {
                        Log.d("LifeInsuranceViewModel", "1")
                        if (s.equals("register")) {
                            Toast.makeText(this, it.item.message.phone!!.get(0), Toast.LENGTH_SHORT)
                                .show()
                            progressDialog.dialog.dismiss()


                        } else {
                            startPhoneNumberVerification("+959" + edt_registername.text.toString().trim())
                        }
                    } else {
                        if (s.equals("register"))
                            startPhoneNumberVerification("+959" + edt_registername.text.toString().trim())
                        else {
                            Toast.makeText(
                                applicationContext, "The Number is Not Register",
                                Toast.LENGTH_LONG
                            ).show()
                            progressDialog.dialog.dismiss()
                        }
                    }

                }
                is NetWorkViewState.Error -> {
                    Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_LONG).show()
                    progressDialog.dialog.dismiss()
                }
            }
        })
    }
//    fun optCheck(){
//        if(progressDialog.dialog.isShowing)
//            progressDialog.textView.text = "Sending OTP.."
//        if (edt_registername.text.toString().trim().length > 5) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://solutionshubservice.com/otpservice/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            val otpinterface = retrofit.create(ApiInterface::class.java)
//            val call = otpinterface.sendOtp(
//                "MI",
//                "GIfl32tnaia5jTZyBsqfFaym8fQnEPzH",
//                "09"+edt_registername.text.toString().trim()
//
//            )
//
//
//            call.enqueue(object : Callback<OtpFormat> {
//                override fun onFailure(call: Call<OtpFormat>, t: Throwable) {
//
//                    if(progressDialog.dialog.isShowing){
//                        progressDialog.dialog.dismiss()
//                    }
//                    Toast.makeText(applicationContext,"something wrong",Toast.LENGTH_LONG).show()
//                }
//
//                override fun onResponse(call: Call<OtpFormat>, response: Response<OtpFormat>) {
//                    if(progressDialog.dialog.isShowing){
//                        progressDialog.dialog.dismiss()
//                    }
//
//                    if(response.isSuccessful && response.body() != null){
//                        Log.d("test","${response.body()}")
//                        if(response.body()!!.status == "SUCCESS"){
//                            val intent = Intent(applicationContext,ConfirmActivity::class.java)
//                            intent.putExtra("phone","09"+edt_registername.text.toString().trim())
//                            intent.putExtra("parent",s)
//                            startActivity(intent)
//                            finish()
//                        }
//                        else{
//                            Toast.makeText(applicationContext,response.errorBody().toString(),Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//                }
//
//            })
//
//
//        }
//        else{
//            Toast.makeText(applicationContext,"Phone Number Correctly",Toast.LENGTH_SHORT).show()
//        }
//    }


    fun otpFirebaseCheck() {

    }

    fun hideSend(){
        phone_register.visibility = View.GONE
        register_next.visibility = View.GONE
        cardView.visibility = View.GONE
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks

    }
    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String,phone : String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        Log.d("test","id is $credential code is ${phone}")
        signInWithPhoneAuthCredential(credential,phone)
        // [END verify_with_code]


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,phone : String) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(progressDialog.dialog.isShowing){
                        progressDialog.dialog.dismiss()
                    }
                    Log.d("test", "signInWithCredential:success")

                    if(s.equals("register")) {
                        val intent = Intent(context, RegisterActivity::class.java)
                        intent.putExtra("phone", "09" +edt_registername.text.toString())
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(context, ResetPasswordActivity::class.java)
                        intent.putExtra("phone", "09" +edt_registername.text.toString())
                        startActivity(intent)
                        finish()
                    }

                } else {
                    if(progressDialog.dialog.isShowing){
                        progressDialog.dialog.dismiss()
                    }
                    // Sign in failed, display a message and update the UI
                    Log.w("test", "signInWithCredential:failure", task.exception)

                    Toast.makeText(applicationContext,"error",Toast.LENGTH_SHORT).show()

                }
            }
    }

}
