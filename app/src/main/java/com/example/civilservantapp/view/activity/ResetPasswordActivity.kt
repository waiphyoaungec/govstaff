package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_reset_password.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class ResetPasswordActivity : AppCompatActivity() {

    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        MDetect.init(this)
        progressDialog= ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("ပြန်လည်သတ်မှတ်နေပါသည်")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        if(MDetect.isUnicode()){
            toolbar.title = "စကားဝှက်ပြန်လျှောက်မည်"
            progressDialog.setMessage("ပြန်လည်သတ်မှတ်နေပါသည်")
            lblphno.text = "ဖုန်းနံပါတ်"
            lblpassword.text = "လျို့ဝှက်နံပါတ်"
            edt_resetpassword.hint = getString(R.string.supply_hint)
            lblconfirmpassword.text = "လျို့ဝှက်နံပါတ်"
            edt_resetconfirmpassword.hint = getString(R.string.supply_hint)
        }
        else{
            toolbar.title = "စကားဝွက္ျပန္ေလွ်ာက္မည္"
            progressDialog.setMessage("ျပန္လည္သတ္မွတ္ေနပါသည္")
            lblphno.text = "ဖုန္းနံပါတ္"
            lblpassword.text = "လ်ိဳ႕ဝွက္နံပါတ္"
            edt_resetpassword.hint = Rabbit.uni2zg( getString(R.string.supply_hint))
            lblconfirmpassword.text = "လ်ိဳ႕ဝွက္နံပါတ္"
            edt_resetconfirmpassword.hint =Rabbit.uni2zg( getString(R.string.supply_hint))
            btnResetPassword.text = Rabbit.uni2zg(getString(R.string.btn_reset))
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        var edtPhone = intent.getStringExtra("phone")
        edt_resetrphone.setText(edtPhone)
        fetchDataViewModel.checkPhoneData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.message.equals("success")){
                        Log.d("Reset", it.item.message+"\t"+it.item.data)
                        fetchDataViewModel.resetPassword(
                            edt_resetpassword.text.toString(),
                            edt_resetconfirmpassword.text.toString(),
                            it.item.data!!
                        )
                    }else{
                        Toast.makeText(this, "ဖုန်းနံပါတ်စစ်ဆေးမှုမအောင်မြင်ပါ", Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        fetchDataViewModel.changePasswordData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.message.equals("success")){
                        Toast.makeText(applicationContext,"Successful",Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, it.item.data, Toast.LENGTH_SHORT).show()
                        Thread.sleep(5000)
                        this.finish()
                    }
                    else{
                        Toast.makeText(applicationContext,it.item.message,Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    Toast.makeText(applicationContext,"Something wrong",Toast.LENGTH_SHORT).show()
                    Log.d("Reset", it.errormessage.toString())
                    progressDialog.dismiss()
                }
            }
        })
        btnResetPassword.setOnClickListener {
            if(checkFields()){
                passwordValidate()
            }else{
                Toast.makeText(this, "အချက်အလက်အားလုံးဖြည့်စွက်ပါ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkFields(): Boolean{
        return edt_resetrphone.text.toString()!="" && edt_resetpassword.text.toString()!="" && edt_resetconfirmpassword.text.toString()!=""
    }
    private fun passwordValidate(){
        if(edt_resetpassword.text.toString().trim().length>=6 && edt_resetconfirmpassword.text.toString().trim().length>=6){
            if(edt_resetpassword.text.toString() == edt_resetconfirmpassword.text.toString()){
                fetchDataViewModel.checkPhone(edt_resetrphone.text.toString())
            }
            else{
                Toast.makeText(applicationContext,"Password Do not match",Toast.LENGTH_SHORT).show()
                edt_resetpassword.error = "Password Do not match"
                edt_resetconfirmpassword.error = "Password Do not match"
            }
        }
        else{

            Toast.makeText(applicationContext,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show()
        }
    }
}
