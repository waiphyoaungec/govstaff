package com.example.civilservantapp.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.view.activity.ExistingPolicyId
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import kotlinx.android.synthetic.main.activity_register.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(
        LifeInsuranceViewModel::class.java) }
    var name: String? = ""
    var context = this
    var username: String? = ""
    var email: String? = ""
    var phone: String?=""
    var password: String? = ""
    var passwordConfirmation: String? = ""
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        MDetect.init(this)

        progressDialog = ProgressDialog(this)

        if(MDetect.isUnicode()){
            btnRegister.setText(Rabbit.zg2uni(getString(R.string.btn_rg_save)))
            toolbar.title = Rabbit.zg2uni(getString(R.string.title_rg))
            lblname.text = "အမည်"
            lblphno.text = "ဖုန်းနံပါတ်"
            lblpassword.text = "လျို့ဝှက်နံပါတ်"
            edt_registerpassword.hint = "ဖြည့်စွက်ပါ"
            lblconfirmpassword.text = "လျို့ဝှက်နံပါတ်"
            edt_registerconfirmpassword.hint = "ဖြည့်စွက်ပါ"



        }else{
            toolbar.title = getString(R.string.title_rg)
            lblname.text = "အမည္"
            lblphno.text = "ဖုန္းနံပါတ္"
            lblpassword.text = "လ်ိဳ႕ဝွက္နံပါတ္"
            edt_registerpassword.hint = "ျဖည့္စြက္ပါ"
            lblconfirmpassword.text = "လ်ိဳ႕ဝွက္နံပါတ္"
            edt_registerconfirmpassword.hint = "ျဖည့္စြက္ပါ"
            btnRegister.text = getString(R.string.btn_rg_save)
        }
        progressDialog.setMessage("Registering")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        var no = intent.getStringExtra("phone")
        edt_registerphone.setText(no)
        edt_registerphone.isEnabled = false

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        lifeInsuranceViewModel.registData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    progressDialog.dismiss()

                    if(it.item.message.phone!=null){
                        Log.d("LifeInsuranceViewModel","1")
                        finish()
                        Toast.makeText(this, it.item.message.phone!!.get(0), Toast.LENGTH_SHORT).show()
                    }
                    if(it.item.message.username!=null){
                        Log.d("LifeInsuranceViewModel","2")
                        Toast.makeText(this, it.item.message.username!!.get(0), Toast.LENGTH_SHORT).show()
                    }
                    if(it.item.message.password!=null){
                        Log.d("LifeInsuranceViewModel","3")
                        Toast.makeText(this, it.item.message.password!!.get(0), Toast.LENGTH_SHORT).show()
                    }
                    if(it.item.message.msg!=""){
                        Log.d("LifeInsuranceViewModel","4")
                        AlertDialog.Builder(context)
                            .setMessage("ယခင်ပေါ်လစီအိုင်ဒီရှိပါကချိတ်ရန်...?")
                            .setCancelable(false)
                            .setTitle("လျှောက်ပြီးသားပေါ်လစီအိုင်ဒီ")
                            .setPositiveButton("ချိတ်မည်") { arg0, arg1 ->
                                val intent = Intent(context, ExistingPolicyId::class.java)
                                intent.putExtra("id",it.item.data)
                                startActivity(intent)
                                finish()
                            }
                            .setNegativeButton("မချိတ်ပါ") { arg0, arg1 ->
                                val intent = Intent(context,LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .show()
                        Toast.makeText(this, it.item.message.msg, Toast.LENGTH_SHORT).show()

                    }
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view!! == btnRegister){
            name = edt_registername.text.toString()
            username = edt_registerphone.text.toString()
            //email = edt_registeremail.text.toString()
            password = edt_registerpassword.text.toString()
            passwordConfirmation = edt_registerconfirmpassword.text.toString()
            phone = edt_registerphone.text.toString()
            if(name!="" && username!="" && password!="" && passwordConfirmation!="" && phone!=""){
                lifeInsuranceViewModel.RegisterUser(name!!, username!!, email!!, phone!!, password!!, passwordConfirmation!!)

            }else{
                Toast.makeText(this, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
