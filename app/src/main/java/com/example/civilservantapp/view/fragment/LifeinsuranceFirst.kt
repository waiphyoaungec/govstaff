package com.example.civilservantapp.view.fragment


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.civilservantapp.R
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.view.LifeinsuranceSecond
import com.example.civilservantapp.view.activity.LifeInsuranceSubFirstActivity
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_lifeinsurance_first.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class LifeinsuranceFirst : Fragment() {
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(
        LifeInsuranceViewModel::class.java) }

    private var amtList: ArrayList<String> = arrayListOf()
    private var officeList: ArrayList<String> = arrayListOf()
    private var amHashMap : HashMap<String, String> = HashMap()
    private var lengthList: ArrayList<String> = arrayListOf()
    private var lengthHashMap: HashMap<String, String> = HashMap()
    private var yearList: ArrayList<Int> = arrayListOf()
    private var monthList: ArrayList<Int> = arrayListOf()
    private var dayList: ArrayList<Int> = arrayListOf()
    private var footList: ArrayList<Int> = arrayListOf()
    private var inchList: ArrayList<Int> = arrayListOf()

    private var mainnrcHashMap : HashMap<String, String> = HashMap()
    private var nrcsubHashMap : HashMap<String, String> = HashMap()
    private var lastnrcHashMap: HashMap<String, String> = HashMap()
    private var stateRegionHashMap: HashMap<String, Int> = HashMap()
    private var districtHashMap: HashMap<String, Int> = HashMap()
    private var townshipHashMap: HashMap<String, Int> = HashMap()
    private var ministryHashMap: HashMap<String, Int> = HashMap()
    private var departmentHashMap: HashMap<String, Int> = HashMap()

    private var TAG : String = "LifeInsuranceFirst"
    private var issurance_amt: String = ""
    private var issurance_period: String = ""
    private var firstnrc: String=""
    private var secondnrc: String=""
    private var thirdnrc: String=""
    private var startyear: Int = 2002
    private var startmonth: Int =0
    private var startday: Int = 0

    private var name: String?=""
    private var dob: String?=""
    private var nrc: String?=""
    private var permanent_addr: String?=""
    private var birth_place: String?=""
    private var gender: String?=""
    private var fathername: String?=""
    private var mark: String?=""
    private var height: String?=""
    private var weight: String?=""
    private var officedept: String?=""
    private var occupation: String?=""
    private var salary: Int?=0
    private var officeId: Int?=0
    private var insuranceamt: Int?=0
    private var insuranceperiod: Int?=0
    private var deposit_amount: Int?=0
    private var townshipid: Int?=0
    private var departmentid: Int?=0

    var snrcList : ArrayList<String> = arrayListOf()
    var snrcHashMap: HashMap<String, String> = HashMap()
    var stateRegionList: ArrayList<String> = arrayListOf()
    var districtList: ArrayList<String> = arrayListOf()
    var townshipList: ArrayList<String> = arrayListOf()
    var ministryList: ArrayList<String> = arrayListOf()
    var departmentList: ArrayList<String> = arrayListOf()
    lateinit var progressDialog: ProgressDialog

    /*
    preview information data
     */
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lifeinsurance_first, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressDialog = ProgressDialog(activity)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        lifeInsuranceViewModel.getInsuranceAmtList()
        lifeInsuranceViewModel.getOfficeList()
        lifeInsuranceViewModel.get_StateRegionData()
        bind_officeSpinner()
        bind_nrcSpinner()

        lifeInsuranceViewModel.nrcListData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    //clear the existing list and add updated data to list
                    snrcList.clear()
                    snrcHashMap.clear()
                    snrcList.add("ရွေးချယ်ရန်")
                    snrcHashMap.put("ရွေးချယ်ရန်", "0")
                    for(i in 0..it.item.size-1){
                        snrcList.add(it.item.get(i).mm_short_district!!)
                        snrcHashMap.put(it.item.get(i).mm_short_district!!, it.item.get(i).long_district!!)
                    }
                    var snrcAdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, snrcList)
                    spn_snrc.adapter = snrcAdapter
                    snrcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    snrcAdapter.notifyDataSetChanged()
                    //progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.hide()
                }
            }
        })

        spn_fnrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })
        spn_snrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })
        spn_tnrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })

        btn_firstform.setOnClickListener {
            if(check_Fields()){
                Toast.makeText(activity!!, "ဒေတာများကိုဖြည့်ပါ", Toast.LENGTH_SHORT).show()
            }else{
                Sendto_FirstSubForm()
            }
        }
    }
    fun get_nrcById(id: Int){
        lifeInsuranceViewModel.getNrcById(id)
    }
    fun bind_nrcSpinner(){
        /*
        bind main nrc Data 1..14
         */
        var fnrcLength = resources.getStringArray(R.array.fnrc).size
        mainnrcHashMap.put("ရွေးချယ်ရန်", "0")
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

        var nrc_no = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.fnrc))
        nrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_fnrc.adapter = nrc_no

        spn_fnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                firstnrc = mainnrcHashMap.get(adapterView!!.getItemAtPosition(position)).toString()
                Log.d(TAG, "firstly"+firstnrc)
                get_nrcById(firstnrc.toInt())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spn_snrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                secondnrc = snrcHashMap.get(adapterView!!.getItemAtPosition(position)).toString()
                //Toast.makeText(this@LifeInsuranceFirst, firstnrc+"/"+secondnrc, Toast.LENGTH_SHORT).show()
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
        var lastnrc_no = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, lastArrayList)
        lastnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_tnrc.adapter = lastnrc_no
        spn_tnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adpterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                thirdnrc = lastnrcHashMap.get(adpterView!!.getItemAtPosition(position)).toString()
                Log.d(TAG, firstnrc+"/"+secondnrc+thirdnrc)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun bind_officeSpinner(){
        lifeInsuranceViewModel.officeList.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    officeList.add("ရွေးချယ်ရန်")
                    it.item.map {
                        if(!MDetect.isUnicode()){
                            it.office_name = Rabbit.uni2zg(it.office_name)
                        }
                        officeList.add(it.office_name!!)
                    }
                    var officeAdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, officeList)
                    officeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    form_spinneroffice.adapter = officeAdapter
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
    }

    fun Sendto_FirstSubForm(){
        val intent = Intent(activity!!, LifeInsuranceSubFirstActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("nrc", nrc)
        intent.putExtra("gender", gender)
        intent.putExtra("fathername", fathername)
        intent.putExtra("occupation", occupation)
        intent.putExtra("officeId", officeId!!)

        intent.putExtra("townshipid", townshipid)
        intent.putExtra("departmentid", departmentid)
        intent.putExtra("previewofficename", previewofficename)
        intent.putExtra("previewgender", previewgender)
        intent.putExtra("previewtownship", previewtownship)
        intent.putExtra("previewdepartment", previewdepartment)

        startActivity(intent)
    }

    fun hideKeyboard(){
        val view = activity!!.findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun check_Fields(): Boolean{
        if(form_spinneroffice.selectedItemPosition==0 || tie_name.text.toString()=="" || spn_fnrc.selectedItemPosition==0 || spn_snrc.selectedItemPosition==0
            || spn_tnrc.selectedItemPosition == 0 || tie_nrcno.text.toString()=="" || tie_fthname.text.toString()==""){
            return true
        }else{
            return false
        }
    }
}
