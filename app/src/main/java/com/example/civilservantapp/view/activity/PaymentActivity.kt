package com.example.civilservantapp.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import com.example.civilservantapp.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.example.civilservantapp.view.HomeActivity

class PaymentActivity : AppCompatActivity() {

    var easyPayUrl : String = "http://203.81.78.180:8385/epmp-demo-middleware/payment"
    var merchantID: String = "MchtpH9x71002"
    var pin: Int = 4545
    var invoiceNo: String = "B1/1911270000002"
    var amount: Int = 10
    var userDefined1: String="mobile"
    var userDefined2: String = ""
    var userDefined3: String=""
    var txnType: String = ""
    var encryptKey : String = "demo2_h@sH"
    var encryptDataList : ArrayList<String> = arrayListOf()
    var TAG = "PaymentActivity"
    var hashString: String=""
    var hashData: String = ""
    var postData: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setSupportActionBar(toolbar)

        val bundle = intent.extras
        bundle?.let {
            bundle.apply {
                invoiceNo = getString("invoiceNo")!!
            }
        }
        /*
        add encrypt data
         */
        encryptDataList.add(merchantID)
        //encryptDataList.add(pin.toString())
        encryptDataList.add(invoiceNo)
        encryptDataList.add(amount.toString())
        encryptDataList.add(userDefined1)
        Log.d(TAG, Gson().toJson(encryptDataList))

        encryptDataList.sort()
        Log.d(TAG, Gson().toJson(encryptDataList))

        for(i in encryptDataList){
            hashString+=i
        }
        Log.d(TAG, hashString)
        hashData = hash_string(hashString)
        postData = "merchantID="+merchantID+"&pin="+URLEncoder.encode(pin.toString(), "UTF-8")+"&invoiceNo="+
                URLEncoder.encode(invoiceNo, "UTF-8")+"&amount="+URLEncoder.encode(amount.toString()+"UTF-8")+"&userDefined1="+
                URLEncoder.encode(userDefined1, "UTF-8")+"&hashValue="+URLEncoder.encode(hashData, "UTF-8")

        Log.d(TAG, postData)

        var string_demo = "<html>\n" +
                "<body onload=\"document.createElement('form').submit.call(document.getElementById('form'))\">"+
                "<form id=\"form\" method=\"post\" action=\"http://203.81.78.180:8385/epmp-demo-middleware/payment\">\n" +
                "<input type=\"hidden\" name=\"merchantID\" value=" + merchantID + ">\n" +
//                "<input type=\"hidden\" name=\"pin\" value="+ pin +">\n" +
                "<input type=\"hidden\" name=\"invoiceNo\" value="+ invoiceNo +">\n" +
                "<input type=\"hidden\" name=\"amount\" value="+ amount +">\n" +
                "<input type=\"hidden\" name=\"userDefined1\" value="+ userDefined1 +">\n" +
                "<input type=\"hidden\" name=\"userDefined2\" value="+ userDefined2 +">\n" +
                "<input type=\"hidden\" name=\"userDefined3\" value="+ userDefined3 +">\n" +
                "<input type=\"hidden\" name=\"hashValue\" value="+hashData+">\n" +
                "</form></body></html>"

        Log.d(TAG, string_demo)
        paymentwebview.webViewClient = WebViewClient()
        WebView.setWebContentsDebuggingEnabled(true)
        //paymentwebview.webChromeClient = WebChromeClient()
        paymentwebview.settings.javaScriptEnabled=true

         paymentwebview.loadData(string_demo, "text/html", null)

    }

    fun hash_string(message: String):String{
        try {
            val hasher = Mac.getInstance("HmacSHA1")
            hasher.init(SecretKeySpec(encryptKey.toByteArray(), "HmacSHA1"))
            val hash = hasher.doFinal(message.toByteArray())
            return hex(hash)
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: InvalidKeyException) {
        }
        return ""
    }

    /*
    convert byte array to hex string
     */
    fun hex(data: ByteArray): String = data.fold(StringBuilder()) { acc, next ->
        acc.append(String.format("%02x", next))
    }.toString().toLowerCase()

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.payment_menuitem, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.payment_finish){
            val homeIntent = Intent(this, ParentHomeActivity::class.java)
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
