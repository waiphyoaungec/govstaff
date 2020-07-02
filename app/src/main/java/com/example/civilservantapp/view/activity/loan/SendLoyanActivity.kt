package com.example.civilservantapp.view.activity.loan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.download_extension.DownloadPdf
import com.example.civilservantapp.download_extension.ShowAlert
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import kotlinx.android.synthetic.main.activity_get_contract.*
import kotlinx.android.synthetic.main.activity_send_loyan.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class SendLoyanActivity : AppCompatActivity() {
    private var mainnrcHashMap : HashMap<String, String> = HashMap()
    private var lastnrcHashMap: HashMap<String, String> = HashMap()
    private var firstnrc: String=""
    lateinit var person_id : String
    lateinit var policy_id : String
    private var secondnrc: String=""
    private  lateinit var loanamt : String
    var snrcList : ArrayList<String> = arrayListOf()
    var snrcHashMap: HashMap<String, String> = HashMap()
    private var thirdnrc: String=""
    lateinit var customProgressBar: CustomProgressBar


    val fetDataViewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProvider(this).get(
        LifeInsuranceViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_loyan)
        requestPermission()
        customProgressBar = CustomProgressBar()
        person_id = intent.getStringExtra("id")!!
        policy_id = intent.getStringExtra("policy_id")!!
        loanamt = intent.getStringExtra("loanamt")!!
        tool_send_loyan.title = "ငွေချေးယူရန်.. "
        tool_send_loyan.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        setSupportActionBar(getcontract_toolbar)
        tool_send_loyan.setNavigationOnClickListener {
            finish()
        }
        btn_send_loyal.setOnClickListener {
            if(sendLoanCheck()){
                if(checkPermission()) {
                    sendLoan(
                        person_id.toInt(),
                       "$loanamt",
                        2,
                        loyal_name.text.toString().trim(),
                        "$firstnrc/$secondnrc$thirdnrc${loyan_nrc.text}",
                        loyal_address_edt.text.toString(),
                        "09" + loyal_phone.text.toString(),
                        loyal_ammounmt.text.toString()
                    )
                }
                else{
                    requestPermission()
                }
            }
        }
        bind_nrcSpinner()
        lifeInsuranceViewModel.nrcListData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    customProgressBar.show(this,"Loading...")
                }
                is NetWorkViewState.Success->{
                    //clear the existing list and add updated data to list
                    customProgressBar.dialog.dismiss()
                    snrcList.clear()
                    snrcHashMap.clear()
                    snrcList.add("ရွေးချယ်ရန်")
                    snrcHashMap.put("ရွေးချယ်ရန်", "0")
                    for(i in 0..it.item.size-1){
                        snrcList.add(it.item.get(i).mm_short_district!!)
                        snrcHashMap.put(it.item.get(i).mm_short_district!!, it.item.get(i).long_district!!)
                    }
                    var snrcAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, snrcList)
                    spn_snrc.adapter = snrcAdapter
                    snrcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    snrcAdapter.notifyDataSetChanged()

                }
                is NetWorkViewState.Error->{
                     customProgressBar.dialog.dismiss()
                }
            }
        })


        fetDataViewModel.sendLoan.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    Log.d("test","Loading")
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Success->{
                    customProgressBar.dialog.dismiss()
                    Log.d("test","success")
                    writeToSDFile(it.item.message.data[0])

                }
                is NetWorkViewState.Error->{
                    customProgressBar.dialog.dismiss()
                    Toast.makeText(applicationContext,"တစ်ခုခုမှားယွင်းနေပါသည်..",Toast.LENGTH_SHORT).show()
                    Log.d("test",it.errormessage.localizedMessage)
                }
            }
        })
    }
    fun bind_nrcSpinner(){
        var fnrcLength = resources.getStringArray(R.array.fnrc).size
        if(MDetect.isUnicode()){
            mainnrcHashMap.put("ရွေးချယ်ရန်", "0")
        }else{
            mainnrcHashMap.put("ေရြးခ်ယ္ရန္", "0")
        }
        for(i in 0..fnrcLength-1){
            mainnrcHashMap.put(resources.getStringArray(R.array.fnrc).get(i), (i).toString())
        }

        if(MDetect.isUnicode()){
            lastnrcHashMap.put(Rabbit.zg2uni("(ႏိုင္)"), "(N)")
            lastnrcHashMap.put(Rabbit.zg2uni("(ျပဳ)"), "(P)")
            lastnrcHashMap.put(Rabbit.zg2uni("(ဧည့္)"), "(E)")
            lastnrcHashMap.put(Rabbit.zg2uni("(သာ)"), "(T)")
        }else{
            lastnrcHashMap.put("(ႏိုင္)", "(N)")
            lastnrcHashMap.put("(ျပဳ)", "(P)")
            lastnrcHashMap.put("(ဧည့္)", "(E)")
            lastnrcHashMap.put("(သာ)", "(T)")
        }

        var nrc_no = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(
            R.array.fnrc))
        nrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_fnrc.adapter = nrc_no


        spn_fnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                firstnrc = mainnrcHashMap.get(adapterView!!.getItemAtPosition(position)).toString()
                get_nrcById(firstnrc.toInt())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spn_snrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                secondnrc = snrcHashMap.get(adapterView!!.getItemAtPosition(position)).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        var lastArrayList: ArrayList<String> = arrayListOf()
        if(MDetect.isUnicode()){
            for (i in 0..resources.getStringArray(R.array.lastnrc).size-1){
                lastArrayList.add(Rabbit.zg2uni(resources.getStringArray(R.array.lastnrc).get(i)))
            }
        }else{
            for (i in 0..resources.getStringArray(R.array.lastnrc).size-1){
                lastArrayList.add(resources.getStringArray(R.array.lastnrc).get(i))
            }
        }
        var lastnrc_no = ArrayAdapter(this, android.R.layout.simple_spinner_item, lastArrayList)
        lastnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_tnrc.adapter = lastnrc_no
        spn_tnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adpterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                thirdnrc = lastnrcHashMap.get(adpterView!!.getItemAtPosition(position)).toString()
                Log.d("test", firstnrc+"/"+secondnrc+thirdnrc)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
    fun get_nrcById(id: Int){
        lifeInsuranceViewModel.getNrcById(id)
    }
    fun sendLoanCheck() : Boolean{
        if(loyal_name.text.toString().trim().isEmpty()){
            toast("အမည်ဖြည့်ပါ..")
            loyal_name.error = "required"
            return false
        }
        else if(spn_tnrc.adapter.isEmpty || spn_fnrc.adapter.isEmpty || spn_snrc.adapter.isEmpty){
            toast("မှတ်ပုံတင်ဖြည့်ပါ...")
            return false
        }
        else if(firstnrc.equals("0") || secondnrc.equals("0") || thirdnrc.isEmpty() || loyan_nrc.text.toString().trim().isEmpty()){
            toast("မှတ်ပုံတင်ဖြည့်ပါ")
            return  false
        }
        else if(!(thirdnrc.equals("(N)") || thirdnrc.equals("(P)") || thirdnrc.equals("(E)") || thirdnrc.equals("(T)"))){
            toast("မှတ်ပုံတင်ဖြည့်ပါ")
            return false
        }
        else if(loyal_address_edt.text.toString().trim().isEmpty()){
            toast("လိပ်စာဖြည့်ပါ...")
            return  false
        }
        else if(loyal_phone.text.toString().trim().isEmpty()){
            toast("ဖုန်းနံပါတ်ဖြည့်ပါ...")
            return false
        }
        else if(loyal_ammounmt.text.toString().trim().isEmpty()) {
            toast("ချေးလိုသောငွေပမာဏကိုဖြည့်ပါ...")
            return false
        }


        return true

    }
    fun sendLoan(insur_id : Int,loyan_amount : String,claim_id : Int,witness_name : String,nrc : String,address : String,
                 phone : String,loanamt : String){
        fetDataViewModel.sendLoan(policy_id,insur_id,loyan_amount,claim_id,witness_name,
            nrc,address,phone,loanamt)
    }
    fun Context.toast(message:String){
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
    }
    private fun writeToSDFile(s : String) { // Find the root of the external storage.

           val downloadPdf = DownloadPdf(this)

           if (Build.VERSION.SDK_INT in 23..28) {
                Log.d("test", "23")
                if (checkPermission()) {
                    val uri = downloadPdf.downLoadMiddle("loan.pdf",s)
                    ShowAlert(this).show(uri)
                } else {
                    requestPermission() // Code for permission
                }
            }
            else if(Build.VERSION.SDK_INT >= 29){
                if(checkPermission()){
                    val uri = downloadPdf.downloadQ("loan.pdf",s)
                    ShowAlert(this).showQ(uri)
                }
                else{
                    requestPermission()
                }
            }
            else {
                if(checkPermission()) {
                    val uri = downloadPdf.downloadLower("loan.pdf",s)
                    ShowAlert(this).show(uri)
                }
               else{
                    requestPermission()
                }

        }
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)

    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
        val result1 = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> if (grantResults.size > 0) {
                val readFile = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeFile = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readFile && writeFile)
                   Log.d("test","Successfullt")
                else {
                    Toast.makeText(applicationContext, "Please allow permission", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}