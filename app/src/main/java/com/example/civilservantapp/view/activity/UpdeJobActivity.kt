package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.civilservantapp.R
import com.example.civilservantapp.adapter.ProfileAdapter
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkState
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.profile.InsurancePersonInfo
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import kotlinx.android.synthetic.main.activity_upde_job.*

class UpdeJobActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    private var user_id: String = ""
    private var policy_id: String = ""
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(
        LifeInsuranceViewModel::class.java)
    }
    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }
    var stateRegionList: ArrayList<String> = arrayListOf()
    private var stateRegionHashMap: HashMap<String, Int> = HashMap()
    var districtList: ArrayList<String> = arrayListOf()
    private var districtHashMap: HashMap<String, Int> = HashMap()
    var townshipList: ArrayList<String> = arrayListOf()
    private var townshipHashMap: HashMap<String, Int> = HashMap()
    var ministryList: ArrayList<String> = arrayListOf()
    private var ministryHashMap: HashMap<String, Int> = HashMap()
    lateinit var progressDialog: ProgressDialog
    var departmentList: ArrayList<String> = arrayListOf()
    private var departmentHashMap: HashMap<String, Int> = HashMap()

    /*
    bind information
     */
    private var bindname : String = ""
    private var bindpolicyid: String = ""
    private var bindoccupation: String = ""
    private var bindstateid: Int = 0
    private var binddistrictid: Int = 0
    private var bindtownshipid: Int = 0
    private var bindministryid: Int = 0
    private var binddeptid: Int = 0

    private var bindstatevalue: String=""
    private var binddistrictvalue: String=""
    private var bindtownshipvalue: String=""
    private var bindministryvalue: String = ""
    private var binddepartvalue: String=""
    private var bindinsurance_personid: Int = 0
    lateinit var adapter : ProfileAdapter
    lateinit var insurancePersonInfo: List<InsurancePersonInfo>

    private var statecheck: Int =0
    private var districtcheck: Int =0
    private var townshipcheck: Int = 0
    private var ministrycheck: Int = 0
    private var depcheck: Int =0
    private var policyIdKey: String = "policy_id"
    private var army_status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upde_job)
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        toolbar.setTitle("အလုပ်ပြောင်းအလွှာ")
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        lifeInsuranceViewModel.stateRegionData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    if(!progressDialog.isShowing){
                        progressDialog.show()
                    }
                }

                is NetWorkViewState.Success->{
                    districtList.clear()
                    districtHashMap.clear()

                    for(i in 0..it.item!!.size-1){
                        districtList.add(it.item!!.get(i).mm_state_region_name!!)
                        districtHashMap.put(it.item!!.get(i).mm_state_region_name!!, it.item!!.get(i).id!!)

                    }
                    var districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districtList)
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_stateregion.adapter = districtAdapter
                    spn_stateregion.setSelection(districtAdapter.getPosition(binddistrictvalue))
                    //progressDialog.hide()
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

                    for(i in 0..it.item.districts!!.size-1){
                        districtList.add(it.item.districts!!.get(i).mm_district_name!!)
                        districtHashMap.put(it.item.districts!!.get(i).mm_district_name!!, it.item.districts!!.get(i).id!!)

                    }
                    var districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districtList)
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_district.adapter = districtAdapter
                    spn_district.setSelection(districtAdapter.getPosition(binddistrictvalue))
                    //progressDialog.hide()
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


                    for(i in 0..it.item.townships!!.size-1){
                        townshipList.add(it.item.townships!!.get(i).mm_township_name!!)
                        townshipHashMap.put(it.item.townships!!.get(i).mm_township_name!!, it.item.townships!!.get(i).id!!)

                    }
                    var townshipAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, townshipList)
                    townshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_township.adapter = townshipAdapter
                    spn_township.setSelection(townshipAdapter.getPosition(bindtownshipvalue))
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.ministryData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    // progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    ministryList.clear()
                    ministryHashMap.clear()

                    for(i in 0..it.item.size-1){
                        ministryList.add(it.item.get(i).mm_ministry_name!!)
                        ministryHashMap.put(it.item.get(i).mm_ministry_name!!, it.item.get(i).id!!)

                    }
                    var ministryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ministryList)
                    ministryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_ministry.adapter = ministryAdapter
                    spn_ministry.setSelection(ministryAdapter.getPosition(bindministryvalue))
                    //progressDialog.dismiss()
                }
            }
        })
        lifeInsuranceViewModel.departmentData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Success->{
                    departmentList.clear()
                    departmentHashMap.clear()

                    for(i in 0..it.item.departments!!.size-1){
                        departmentList.add(it.item.departments!!.get(i).mm_dept_name!!)
                        departmentHashMap.put(it.item.departments!!.get(i).mm_dept_name!!, it.item.departments!!.get(i).id!!)

                    }
                    var departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentList)
                    departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_department.adapter = departmentAdapter
                    spn_department.setSelection(departmentAdapter.getPosition(binddepartvalue))
                    progressDialog.dismiss()
                    //progressDialog.hide()
                    //progressDialog.dismiss()
                }
            }
        })


        sharedPreference = SharedPreference(this)
        user_id = sharedPreference.getUserInfo("user_id")!!

        die_policy.setText(getpolicyNo())
        tie_name.setText(getName())

        if(user_id!=""){
            progressDialog.setMessage("ဒေတာများကိုရယူနေပါသည် ခေတ္တစောင့်ပါ")
            progressDialog.show()
            fetchDataViewModel.getUserProfile(user_id.toInt())

            getStateList()
            getMinistryList()
            getStateRegionList()
            if(!spn_stateregion.isEmpty() && spn_district.isEmpty() && !spn_township.isEmpty() && !spn_ministry.isEmpty()
                && !spn_department.isEmpty()){
                progressDialog.dismiss()
            }
        }


        fetchDataViewModel.getUserProfile.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.hide()

                }
                is NetWorkViewState.Success->{
                    if(it.item.insurance_person_info != null)
                        if(it.item.insurance_person_info!!.size == 1)
                            it.item.insurance_person_info!!.get(0).let {
                                it.apply {



                                }
                            }
                    insurancePersonInfo = it.item.insurance_person_info!!
                    adapter = ProfileAdapter(this,insurancePersonInfo)
                    update_info_list.setHasFixedSize(true)
                    update_info_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                    update_info_list.adapter = adapter
                    update_info.text = "ပေါ်လစီအိုင်ဒီ (${it.item.insurance_person_info!!.size}) ခုလျှောက်ထားသည်"
                    adapter.setOnBtnClick {
                        adapter.selected(it)
                        showInfoList(insurancePersonInfo.get(it))
                    }
                    showInfoList(insurancePersonInfo.get(0))


                }
            }
        })



        btnupdate.setOnClickListener {
            perform_Update()
        }

        lifeInsuranceViewModel.updateJobHistoryData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    if(it.item.message.equals("success")){
                        Toast.makeText(this, "လုပ်ဆောင်ချက်အောင်မြင်ပါသည်", Toast.LENGTH_SHORT).show()
                    }
                    //Thread.sleep(3000)
                    //finish()
                    progressDialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    Toast.makeText(this, "လုပ်ဆောင်ခြင်းမအောင်မြင်ပါ", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        })

    }

    fun getStateRegionList(){
        lifeInsuranceViewModel.stateRegionData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    //progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    stateRegionList.clear()
                    stateRegionHashMap.clear()


                    for(i in 0..it.item.size-1){
                        stateRegionList.add(it.item.get(i).mm_state_region_name!!)
                        stateRegionHashMap.put(it.item.get(i).mm_state_region_name!!, it.item.get(i).id!!)

                    }
                    var stateRegionadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateRegionList)
                    stateRegionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_stateregion.adapter = stateRegionadapter
                    Log.d("Update", bindstatevalue+"\n"+stateRegionadapter.getPosition(bindstatevalue).toString())
                    spn_stateregion.setSelection(stateRegionadapter.getPosition(bindstatevalue))
                    spn_stateregion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                            if(++statecheck>1){
                                hideKeyboard()
                                tie_fjob.requestFocus()
                            }
                            progressDialog.show()
                            getDistrictList(stateRegionHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                    //progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    //progressDialog.hide()
                }
            }
        })
        spn_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hideKeyboard()
                if(++districtcheck>1){
                    tie_fjob.requestFocus()
                }
                progressDialog.show()
                getTownShipList(districtHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spn_township.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hideKeyboard()
                if(++townshipcheck>1){
                    tie_fjob.requestFocus()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        spn_ministry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hideKeyboard()
                if(++ministrycheck>1){
                    tie_fjob.requestFocus()
                    progressDialog.show()
                }
                getDepartmentList(ministryHashMap.get(parent!!.getItemAtPosition(position).toString())!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spn_department.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hideKeyboard()
                if(++depcheck>1){
                    tie_fjob.requestFocus()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }
    fun showInfoList(it : InsurancePersonInfo){
        bindinsurance_personid = it.id
        bindname = it.name
        bindpolicyid = it.policy_id
        bindoccupation = it.occupation
        bindstateid = it.state_regions_id
        binddistrictid = it.district_id
        bindtownshipid = it.townships_id
        bindministryid = it.ministry_id
        binddeptid = it.department_id

        bindstatevalue = it.mm_state_region_name
        binddistrictvalue = it.mm_district_name
        bindtownshipvalue = it.mm_township_name
        bindministryvalue = it.mm_ministry_name
        binddepartvalue = it.mm_dept_name

        Log.d("testtesttest",it.army.toString())




        tie_name.setText(bindname)
        die_policy.setText(bindpolicyid)
        tie_fjob.setText(bindoccupation)
        progressDialog.show()
        lifeInsuranceViewModel.get_StateRegionData()
    }
    fun getStateList(){
        lifeInsuranceViewModel.get_StateRegionData()
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

    fun perform_Update(){

        if(check_Data()){

            progressDialog.setMessage("ပြင်ဆင်နေပါသည် ခေတ္တစောင့်ဆိုင်းပါ")
            var townshipid: Int = townshipHashMap.get(spn_township.getItemAtPosition(spn_township.selectedItemPosition))!!
            var deptid: Int = departmentHashMap.get(spn_department.getItemAtPosition(spn_department.selectedItemPosition))!!
            var occupation: String = tie_fjob.text.toString()
            lifeInsuranceViewModel.updateJobHistory(
                bindinsurance_personid,
                townshipid!!,
                deptid!!,
                occupation,
                "$army_status"
            )
        }else{
            Toast.makeText(this, "အချက်အလက်များကိုပြည့်စုံစွာဖြည့်စွက်ပါ", Toast.LENGTH_SHORT).show()
        }

    }

    fun hideKeyboard(){
        val view = findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getpolicyNo():String{
        if(sharedPreference.getUserInfo(policyIdKey)==null){
            return ""
        }else{
            return sharedPreference.getUserInfo(policyIdKey)!!
        }

    }
    fun getName():String{
        if(sharedPreference.getUserInfo("name")==null){
            return ""
        }else{
            return sharedPreference.getUserInfo("name")!!
        }

    }

    fun check_Data(): Boolean{
        if(!spn_stateregion.adapter.isEmpty && !spn_district.adapter.isEmpty && !spn_township.adapter.isEmpty
            && !spn_ministry.adapter.isEmpty && !spn_department.adapter.isEmpty && tie_name.text.toString()!="" && die_policy.text.toString()!=""
            && tie_fjob.text.toString()!=""){
            return true
        }else{
            return false
        }
    }
}