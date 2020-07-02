package com.example.civilservantapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.civilservantapp.R
import kotlinx.android.synthetic.main.activity_confirm_payment.*
import kotlinx.android.synthetic.main.activity_confirm_payment.toolbar
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import android.content.DialogInterface
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.civilservantapp.view.activity.ParentHomeActivity
import com.example.civilservantapp.view.activity.PaymentActivity


class ConfirmPaymentActivity : AppCompatActivity() {
var paymenttype:String?=""
    var downloadpdfLink :String = "https://solutionshubservice.com/gov_insurance/web/echallan/"
    var policyId: String = ""
    var invoiceNo: String?=""
    var amount: Int=0
    var insurance_person_id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)
        val bundle = intent.extras
        bundle?.let {
            it.apply {
                txtconfirmname.setText(getString("name"))
                policyId = getString("policyid")!!
                txtconfirmpolicyid.setText(policyId)
                invoiceNo = getString("invoiceid")
                txtconfirminvoiceno.setText(invoiceNo)
                txtconfirmpaymentsupply.setText(getString("submitedmonth"))
                paymenttype = getString("paymenttype")
                insurance_person_id = getInt("insurance_person_id")

                if(paymenttype.equals("otc")){
                    txtconfirmpaymenttype.setText("Over the Counter")
                }else if(paymenttype.equals("easypay")){
                    txtconfirmpaymenttype.setText("Mobile Wallet")
                }

                amount = get("amount") as Int
                txtconfirmpaymentcost.setText(amount.toString())
                if(paymenttype.equals("otc")){
                    btnconfirmpayment.setText("ဒေါင်းလုပ်ချရန်")
                }else if(paymenttype.equals("easypay")){
                    btnconfirmpayment.setText("ငွေပေးချေရန်")
                }
            }
        }

            toolbar.title = getString(R.string.life_titleconfirmpayment)
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
        btnconfirmpayment.setOnClickListener {
            if(paymenttype.equals("otc")){
                Log.d("Confirm", downloadpdfLink.plus(insurance_person_id.toString()))
                downloadPDF(downloadpdfLink+insurance_person_id.toString())
                //Thread.sleep(50000)

            }else if(paymenttype.equals("easypay")){
                val easypayIntent = Intent(this, PaymentActivity::class.java)
                with(easypayIntent){
                    putExtra("invoiceNo", invoiceNo)
                    putExtra("amount", amount)
                }
                startActivity(easypayIntent)

            }
        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            Thread.sleep(2000)
            val homeIntent = Intent(this, ParentHomeActivity::class.java)
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeIntent)
        }
    }

    fun downloadPDF(pdflink: String){
        val pdfdownload = Intent(Intent.ACTION_VIEW)
        pdfdownload.setDataAndType(Uri.parse(pdflink), "application/pdf")
        startActivityForResult(pdfdownload, 1)
    }
    fun encodePolicyId(plainString: String):String{
        var newString = Base64.encodeToString(plainString.toByteArray(), Base64.DEFAULT)
        return newString!!
    }

}
