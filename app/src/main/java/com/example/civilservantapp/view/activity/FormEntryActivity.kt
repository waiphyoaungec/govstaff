package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.view.LifeinsuranceSecond
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form_entry.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FormEntryActivity: AppCompatActivity() {

    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(LifeInsuranceViewModel::class.java) }

    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }
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
    private var militray = 0
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
    private var amount: Int?=0

    var snrcList : ArrayList<String> = arrayListOf()
    var snrcHashMap: HashMap<String, String> = HashMap()
    var stateRegionList: ArrayList<String> = arrayListOf()
    var districtList: ArrayList<String> = arrayListOf()
    var townshipList: ArrayList<String> = arrayListOf()
    var ministryList: ArrayList<String> = arrayListOf()
    var departmentList: ArrayList<String> = arrayListOf()
    lateinit var progressDialog: ProgressDialog
    lateinit var sharedPreference: SharedPreference

    /*
    preview information data
     */
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_entry)
        MDetect.init(this)
        lifeInsuranceViewModel.getInsuranceAmtList()
        lifeInsuranceViewModel.getOfficeList()
        lifeInsuranceViewModel.get_StateRegionData()

        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        toolbar.setTitle("အသက်အာမခံအဆိုလွှာ")
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        sharedPreference = SharedPreference(this)
//        if(MDetect.isUnicode()){
//            txt_name.setText(Rabbit.zg2uni(resources.getString(R.string.servname)))
//            tie_name.hint = Rabbit.zg2uni(resources.getString(R.string.servname))
//            lbldob.setText( Rabbit.zg2uni(resources.getString(R.string.serdob)))
//            lblcommingage.setText(Rabbit.zg2uni(resources.getString(R.string.commingage)))
//            tie_commingage.hint = Rabbit.zg2uni(resources.getString(R.string.commingage))
//            lblnrc.text = Rabbit.zg2uni(resources.getString(R.string.nrc_lbl))
//            lblnrc.setText(Rabbit.zg2uni(resources.getString(R.string.nrc_no)))
//            tie_nrcno.hint = Rabbit.zg2uni(resources.getString(R.string.nrc_no))
//            lblpaddr.setText(Rabbit.zg2uni(resources.getString(R.string.peraddr)))
//            tie_paddr.hint = Rabbit.zg2uni(resources.getString(R.string.peraddr))
//            lblbornaddr.setText(Rabbit.zg2uni(resources.getString(R.string.bornaddr)))
//            tie_bornaddr.hint = Rabbit.zg2uni(resources.getString(R.string.bornaddr))
//            rdbtnmale.text = Rabbit.zg2uni(resources.getString(R.string.male))
//            rdbtnfemale.text = Rabbit.zg2uni(resources.getString(R.string.female))
//            lblfathername.setText(Rabbit.zg2uni(resources.getString(R.string.fathername)))
//            tie_fthname.hint = Rabbit.zg2uni(resources.getString(R.string.fathername))
//            lbloccupation.setText(Rabbit.zg2uni(resources.getString(R.string.fulljob)))
//            tie_fjob.hint = Rabbit.zg2uni(resources.getString(R.string.fulljob))
//            lblsalary.setText(Rabbit.zg2uni(resources.getString(R.string.salary)))
//            tie_salary.hint = Rabbit.zg2uni(resources.getString(R.string.salary))
//            lbl_iamount.text = Rabbit.zg2uni(resources.getString(R.string.iamount))
//            lbl_ilength.text = Rabbit.zg2uni(resources.getString(R.string.ilength))
//
//            lbl_bodysign.hint = Rabbit.zg2uni(resources.getString(R.string.lblbodysign))
//            lbl_height.hint = Rabbit.zg2uni(resources.getString(R.string.lblheight))
//            lbl_weight.hint = Rabbit.zg2uni(resources.getString(R.string.lblweight))
//            progressDialog.setMessage(Rabbit.zg2uni(getString(R.string.form_dialog)))
//
//            lblcommingage.setText(Rabbit.zg2uni(getString(R.string.commingage)))
//            lblstatedivision.setText(Rabbit.zg2uni(getString(R.string.lblstatedivision)))
//            lbldistrict.setText(Rabbit.zg2uni(getString(R.string.lbldistrict)))
//            lbltownship.setText(Rabbit.zg2uni(getString(R.string.lbltownship)))
//            lblministry.setText(Rabbit.zg2uni(getString(R.string.lblministry)))
//            lbldepartment.setText(Rabbit.zg2uni(getString(R.string.lbldepartment)))
//            form_lbloffice.setText(Rabbit.zg2uni(getString(R.string.form_lbloffice)))
//            lbl_weight.setText(Rabbit.zg2uni(getString(R.string.lblweight)))
//            tie_weight.hint = Rabbit.zg2uni(getString(R.string.lblweight))
//            lbl_bodysign.setText(Rabbit.zg2uni(getString(R.string.lblbodysign)))
//            tie_bodysign.hint = Rabbit.zg2uni(getString(R.string.lblbodysign))
//            lbl_height.setText(Rabbit.zg2uni(getString(R.string.lblheight)))
//            lbl_malefemale.setText(Rabbit.zg2uni(getString(R.string.preview_gender)))
//        }else{
//            progressDialog.setMessage(getString(R.string.form_dialog))
//        }
        if(sharedPreference.getUserInfo("phone")!=null){
            tie_entryphone.isEnabled = false
            tie_entryphone.setText(sharedPreference.getUserInfo("phone"))

        }
        if(sharedPreference.getUserInfo("name")!=null){

            tie_name.setText(sharedPreference.getUserInfo("name"))


        }
        bind_officeSpinner()
        bind_amtSpinner()
        bind_lengthSpinner()
        bind_nrcSpinner()
        bind_dob_spinners()
        bind_heightspinners()
        get_nrcById(1)
        getStateRegionList()
        getMinistryList()
        lifeInsuranceViewModel.depositAmt.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    //Toast.makeText(this, it.item.deposit_amount.toString(), Toast.LENGTH_SHORT).show()
                    amount = 0
                    amount = it.item.deposit_amount
                    townshipid = townshipHashMap.get(spn_township.getItemAtPosition(spn_township.selectedItemPosition))
                    departmentid = departmentHashMap.get(spn_department.getItemAtPosition(spn_department.selectedItemPosition))

                    previewofficename = form_spinneroffice.selectedItem.toString()
                    if(rdbtnmale.isChecked){
                        previewgender = "male"
                    }else{
                        previewgender = "female"
                    }
                    previewtownship = spn_township.selectedItem.toString()
                    previewdepartment = spn_department.selectedItem.toString()

                    progressDialog.hide()
                    getPayAmount(1, tie_commingage.text.toString().toInt(), insuranceperiod!!, insuranceamt!!)
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
        fetchDataViewModel.getPayAmount.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    deposit_amount =0
                    deposit_amount = it.item
                    Log.d(TAG, amount!!.toString())
                    Sendto_SecondForm()
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
        lifeInsuranceViewModel.nrcListData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
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
                    var snrcAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, snrcList)
                    spn_snrc.adapter = snrcAdapter
                    snrcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    snrcAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.districtData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    districtList.clear()
                    districtHashMap.clear()

                    districtList.add("ရွေးချယ်ရန်")
                    districtHashMap.put("ရွေးချယ်ရန်", 1000000000)
                    for(i in 0..it.item.districts!!.size-1){
                        districtList.add(it.item.districts!!.get(i).mm_district_name!!)
                        districtHashMap.put(it.item.districts!!.get(i).mm_district_name!!, it.item.districts!!.get(i).id!!)

                    }
                    var districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districtList)
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_district.adapter = districtAdapter
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.townshipData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    townshipList.clear()
                    townshipHashMap.clear()

                    townshipList.add("ရွေးချယ်ရန်")
                    townshipHashMap.put("ရွေးချယ်ရန်", 1000000000)
                    for(i in 0..it.item.townships!!.size-1){
                        townshipList.add(it.item.townships!!.get(i).mm_township_name!!)
                        townshipHashMap.put(it.item.townships!!.get(i).mm_township_name!!, it.item.townships!!.get(i).id!!)

                    }
                    var townshipAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, townshipList)
                    townshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_township.adapter = townshipAdapter
                    //progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.ministryData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    ministryList.clear()
                    ministryHashMap.clear()

                    ministryList.add("ရွေးချယ်ရန်")
                    ministryHashMap.put("ရွေးချယ်ရန်", 1000000000)
                    for(i in 0..it.item.size-1){
                        ministryList.add(it.item.get(i).mm_ministry_name!!)
                        ministryHashMap.put(it.item.get(i).mm_ministry_name!!, it.item.get(i).id!!)

                    }
                    var ministryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ministryList)
                    ministryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_ministry.adapter = ministryAdapter
                    progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.departmentData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    departmentList.clear()
                    departmentHashMap.clear()

                    departmentList.add("ရွေးချယ်ရန်")
                    departmentHashMap.put("ရွေးချယ်ရန်", 1000000000)
                    for(i in 0..it.item.departments!!.size-1){
                        departmentList.add(it.item.departments!!.get(i).mm_dept_name!!)
                        departmentHashMap.put(it.item.departments!!.get(i).mm_dept_name!!, it.item.departments!!.get(i).id!!)

                    }
                    var departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentList)
                    departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_department.adapter = departmentAdapter
                    //progressDialog.dismiss()
                }
            }
        })
        spn_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getTownShipList(districtHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        spn_ministry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getDepartmentList(ministryHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        btnsave_finsurance.setOnClickListener {
            if(checkFields()){
                Toast.makeText(this, "ဒေတာများကိုဖြည့်စွက်ပါ", Toast.LENGTH_SHORT).show()
            }else{
                SaveFirstFormData()
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

        var nrc_no = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.fnrc))
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
        var lastnrc_no = ArrayAdapter(this, android.R.layout.simple_spinner_item, lastArrayList)
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

    fun bind_amtSpinner(){
        lifeInsuranceViewModel.amtList.observe(this, Observer {
            Log.d(TAG, Gson().toJson(it))
            amtList.add("ရွေးချယ်ရန်")
            amHashMap.put("ရွေးချယ်ရန်", "")
            it.map {
                amtList.add(it.mm_amt!!)
                amHashMap.put(it.mm_amt!!, it.eng_amt!!)
            }
            var amtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, amtList)
            amtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spn_iamount.adapter = amtAdapter
            spn_iamount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    issurance_amt = amHashMap.get(adapterView!!.getItemAtPosition(position).toString())!!
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }
        })
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
                        officeList.add(it.office_name!!)
                    }
                    var officeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, officeList)
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
    fun bind_lengthSpinner(){
        if(MDetect.isUnicode()){
            lengthList.add("ရွေးချယ်ရန်")
            lengthList.add(Rabbit.zg2uni(resources.getString(R.string.finsurancelength)))
            lengthList.add(Rabbit.zg2uni(resources.getString(R.string.sinsurancelength)))
            lengthList.add(Rabbit.zg2uni(resources.getString(R.string.tinsurancelength)))
            lengthHashMap.put("ရွေးချယ်ရန်", "")
            lengthHashMap.put(Rabbit.zg2uni(resources.getString(R.string.finsurancelength)), "3")
            lengthHashMap.put(Rabbit.zg2uni(resources.getString(R.string.sinsurancelength)), "5")
            lengthHashMap.put(Rabbit.zg2uni(resources.getString(R.string.tinsurancelength)), "10")
        }else{
            lengthList.add(resources.getString(R.string.finsurancelength))
            lengthList.add(resources.getString(R.string.sinsurancelength))
            lengthList.add(resources.getString(R.string.tinsurancelength))
            lengthHashMap.put(resources.getString(R.string.finsurancelength), "3")
            lengthHashMap.put(resources.getString(R.string.sinsurancelength), "5")
            lengthHashMap.put(resources.getString(R.string.tinsurancelength), "10")
        }


        var lengthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lengthList)
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_ilength.adapter = lengthAdapter
        spn_ilength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                issurance_period = lengthHashMap.get(adapterView!!.getItemAtPosition(position).toString())!!
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    fun getStateRegionList(){
        lifeInsuranceViewModel.stateRegionData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    stateRegionList.clear()
                    stateRegionHashMap.clear()

                    stateRegionList.add("ရွေးချယ်ရန်")
                    stateRegionHashMap.put("ရွေးချယ်ရန်", 1000000000)
                    for(i in 0..it.item.size-1){
                        stateRegionList.add(it.item.get(i).mm_state_region_name!!)
                        stateRegionHashMap.put(it.item.get(i).mm_state_region_name!!, it.item.get(i).id!!)

                    }
                    var stateRegionadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateRegionList)
                    stateRegionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_stateregion.adapter = stateRegionadapter
                    spn_stateregion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                            getDistrictList(stateRegionHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
    }
    fun getDistrictList(stateregionid: Int){
        lifeInsuranceViewModel.get_DistrictData(stateregionid)
    }
    fun getTownShipList(districtid: Int){
        lifeInsuranceViewModel.get_TownshipData(districtid)
    }

    fun getMinistryList(){
        lifeInsuranceViewModel.get_MinistryData()
    }
    fun getDepartmentList(ministryid: Int){
        lifeInsuranceViewModel.get_DepartmentData(ministryid)
    }
    fun calculatePremium(age: Int, insuranceperiod: Int, insuranceAmt: Int){
        try{
            lifeInsuranceViewModel.getPremium(age, insuranceperiod, insuranceAmt)

        }catch (e: Exception){

        }

    }
    fun getPayAmount(num_month: Int, age: Int, insurance_peroid: Int, insurance_amount: Int){
        fetchDataViewModel.getPayAmount(num_month, age, insurance_peroid, insurance_amount)
    }

    fun SaveFirstFormData(){
        name = tie_name.text.toString()
        dob = spn_year.selectedItem.toString()+"-"+spn_month.selectedItem.toString()+"-"+spn_day.selectedItem.toString()
        nrc = firstnrc+"/"+secondnrc+thirdnrc+tie_nrcno.text.toString()
        permanent_addr = tie_paddr.text.toString()
        birth_place = tie_bornaddr.text.toString()
        if(rdbtnmale.isChecked){
            gender = "male"
        }else if(rdbtnfemale.isChecked){
            gender="female"
        }
        fathername = tie_fthname.text.toString()
        mark = tie_bodysign.text.toString()
        height = spn_feet.selectedItem.toString()+" feet "+spn_inch.selectedItem.toString() + " inches"
        weight = tie_weight.text.toString()
        //officedept = tie_officedept.text.toString()
        occupation = tie_fjob.text.toString()
        salary = tie_salary.text.toString().toInt()
        officeId = form_spinneroffice.selectedItemPosition+1
        insuranceamt = issurance_amt.toInt()
        insuranceperiod = issurance_period.toInt()
        if(soldier.isChecked){
            militray = 1
        }
        else{
            militray = 0
        }
        Log.d(TAG, dob!!.toString()+"\n"+ insuranceperiod!!.toString()+"\n"+insuranceamt)
        calculatePremium(tie_commingage.text.toString().toInt(), insuranceperiod!!, insuranceamt!!)

        Log.d(TAG, name+"\n"+dob+"\n"+nrc+"\n"+permanent_addr+"\n"+birth_place+"\n"+gender
                +"\n"+fathername+"\n"+mark+"\n"+height+"\n"+weight+"\n"+occupation+"\n"+salary+"\n"+officeId+"\n"+insuranceamt+"\n"+insuranceperiod)



    }

    fun bind_dob_spinners(){
        for(i in 0..38){
            startyear = startyear-1
            yearList.add(startyear)
        }
        for(i in 0..11){
            startmonth = startmonth+1
            monthList.add(startmonth)
        }
        for(i in 0..30){
            startday = startday + 1
            dayList.add(startday)
        }
        var yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_year.adapter = yearAdapter

        var monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monthList)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_month.adapter = monthAdapter

        var dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayList)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_day.adapter = dayAdapter

        spn_year.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val cal = Calendar.getInstance()
                val yr = cal.get(Calendar.YEAR)
                var comminage = (yr-(spn_year.selectedItem).toString().toInt())+1
                tie_commingage.setText(comminage.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spn_year.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })
        spn_stateregion.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_fjob.requestFocus()
            false
        })
        spn_district.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_fjob.requestFocus()
            false
        })
        spn_township.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_fjob.requestFocus()
            false
        })
        spn_ministry.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_paddr.requestFocus()
            false
        })
        spn_department.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_paddr.requestFocus()
            false
        })

        spn_fnrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_nrcno.requestFocus()
            false
        })
        spn_snrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_nrcno.requestFocus()
            false
        })
        spn_tnrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            tie_nrcno.requestFocus()
            false
        })


    }

    fun bind_heightspinners(){
        for(i in 0..2){
            footList.add(i+4)
        }
        for(i in 0..11){
            inchList.add(i+1)
        }

        var heightAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, footList)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_feet.adapter = heightAdapter

        var inchAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inchList)
        inchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_inch.adapter = inchAdapter
    }
    fun Sendto_SecondForm(){
        Log.d(TAG, "townshipid"+townshipid.toString()+"\n"+departmentid+"\n"+tie_commingage.text.toString())
        val get_iamount = amHashMap.get(spn_iamount.selectedItem.toString())
        Log.d(TAG, "insuranceamount"+deposit_amount)
        if(get_iamount!!.toInt()>2000000){
            intent = Intent(this, HealthphotoUploadActivity::class.java)
        }else{
            intent = Intent(this, LifeinsuranceSecond::class.java)
        }
        intent.putExtra("name", name)
        intent.putExtra("dob", dob)
        intent.putExtra("nrc", nrc)
        intent.putExtra("permanent_addr", permanent_addr)
        intent.putExtra("birth_place", birth_place)
        intent.putExtra("gender", gender)
        intent.putExtra("fathername", fathername)
        intent.putExtra("mark", mark)
        intent.putExtra("height", height)
        intent.putExtra("weight", weight)
        //intent.putExtra("officedept",officedept)
        intent.putExtra("occupation", occupation)
        intent.putExtra("salary", salary!!)
        intent.putExtra("officeId", officeId!!)
        intent.putExtra("insuranceamt", insuranceamt!!)
        intent.putExtra("insuranceperiod", insuranceperiod!!)
        intent.putExtra("deposit_amount", deposit_amount)
        intent.putExtra("townshipid", townshipid)
        intent.putExtra("departmentid", departmentid)
        intent.putExtra("previewofficename", previewofficename)
        intent.putExtra("previewgender", previewgender)
        intent.putExtra("previewtownship", previewtownship)
        intent.putExtra("previewdepartment", previewdepartment)
        intent.putExtra("age_next_year", tie_commingage.text.toString().toInt())
        intent.putExtra("amount", amount)
        intent.putExtra("phone_no", tie_entryphone.text.toString())
        intent.putExtra("soldier",militray)

        startActivity(intent)
    }

    fun hideKeyboard(){
        val view = findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun checkFields(): Boolean{
        if(tie_name.text.toString()=="" || tie_nrcno.text.toString()=="" || tie_salary.text.toString()=="" || tie_weight.text.toString()=="" ||
            tie_entryphone.text.toString()=="" || spn_iamount.selectedItemPosition==0 || spn_ilength.selectedItemPosition==0 || spn_fnrc.selectedItemPosition==0 ||
            spn_snrc.selectedItemPosition==0 || spn_tnrc.selectedItemPosition==0 || form_spinneroffice.selectedItemPosition == 0){
            return true
        }else{
            return false
        }
    }
}