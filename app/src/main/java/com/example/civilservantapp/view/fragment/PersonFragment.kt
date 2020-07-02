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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.beneficiary.BeneficiaryPercent
import com.example.civilservantapp.model.beneficiary.Beneficiarys
import com.example.civilservantapp.util.InputFilter
import com.example.civilservantapp.view.PreviewStateActivity
import com.example.civilservantapp.view.activity.ThirdInsuranceLifeActivity
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_person.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.HashMap

class PersonFragment : Fragment() {
    public val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(
        LifeInsuranceViewModel::class.java) }
    private var TAG="LifeInsuranceThird"
    /*
    variable for third
     */
    var edtbname: String=""
    var edtbnrc: String=""
    var edtbfthName: String=""
    var edtrelationship: String=""
    var edtage: Int=0
    var edtpercent: Double=0.0
    var bname: String = ""
    var bnrc: String = ""
    var fthName: String = ""
    var relationship: String = ""
    var age: Int=0
    var percent: Double=0.0
    var bnameList: ArrayList<String> = arrayListOf()
    var bnrcList: ArrayList<String> = arrayListOf()
    var fthNameList: ArrayList<String> = arrayListOf()
    var relationshipList: ArrayList<String> = arrayListOf()
    var ageList: ArrayList<Int> = arrayListOf()
    var percentList: ArrayList<BeneficiaryPercent> = arrayListOf()
    var userId: String?=""
    var beneficiaryList: ArrayList<Beneficiarys> = arrayListOf()
    /*
   variable for first
    */
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
    /*
    for Second Form
     */
    private var previous_premium_status: Int =0
    private var previous_premium_date: String=""
    private var previous_acceptance_status: Int =0
    private var drug_satus: Int=0
    private var medicial_status: ArrayList<String> = arrayListOf()

    lateinit var sharedPreference: SharedPreference
    lateinit var progressDialog: ProgressDialog
    private var mainnrcHashMap : HashMap<String, String> = HashMap()
    private var firstnrc: String=""
    private var secondnrc: String=""
    private var thirdnrc: String=""
    var snrcList : ArrayList<String> = arrayListOf()
    var snrcHashMap: HashMap<String, String> = HashMap()
    private var lastnrcHashMap: HashMap<String, String> = HashMap()

    /*
preview information data
 */
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    private var previewmedicalList: ArrayList<String> = arrayListOf()

    //third
    lateinit var thirdInsuranceLifeActivity: ThirdInsuranceLifeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = activity!!.intent.extras
        sharedPreference = SharedPreference(activity!!)
        progressDialog = ProgressDialog(activity!!)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//        bundle?.let {
//            bundle.apply {
//                //get intent with data
//                /*
//                for first
//                 */
//                name = getString("name")
//                dob = getString("dob")
//                nrc = getString("nrc")
//                permanent_addr = getString("permanent_addr")
//                birth_place = getString("birth_place")
//                gender = getString("gender")
//                fathername = getString("fathername")
//                mark = getString("mark")
//                height = getString("height")
//                weight = getString("weight")
//                //officedept = getString("officedept")
//                occupation = getString("occupation")
//                salary = bundle.get("salary") as Int
//                officeId = bundle.get("officeId") as Int
//                insuranceamt = bundle.get("insuranceamt") as Int
//                insuranceperiod = bundle.get("insuranceperiod") as Int
//                deposit_amount = bundle.get("deposit_amount") as Int
//                townshipid = bundle.get("townshipid") as Int
//                departmentid = bundle.get("departmentid") as Int
//                previewofficename = getString("previewofficename")
//                previewgender = getString("previewgender")
//                previewtownship = getString("previewtownship")
//                previewdepartment = getString("previewdepartment")
//
//                /*
//                for second
//                 */
//                previous_premium_status = bundle.get("previous_premium_status") as Int
//                previous_premium_date = getString("previous_premium_date")!!
//                previous_acceptance_status = bundle.get("drug_satus") as Int
//                drug_satus = bundle.get("drug_satus") as Int
//                medicial_status = getStringArrayList("medicial_status")!!
//                previewmedicalList = getStringArrayList("previewmedicalList")!!
//                /*
//                for third
//                 */
//                userId = sharedPreference.getUserInfo("userid").toString()
//            }
//
//        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        thirdInsuranceLifeActivity = (activity!! as ThirdInsuranceLifeActivity)
//        if(MDetect.isUnicode()){
//            progressDialog.setMessage(Rabbit.zg2uni(getString(R.string.form_dialog)))
//
//            btnAddAll.setText(Rabbit.zg2uni(getString(R.string.btnsaveall)))
//            lblbeneficiary.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiary)))
//            edt_beneficiaryname.hint = Rabbit.zg2uni(getString(R.string.lblbeneficiaryname))
//            edt_beneficiaryage.hint = Rabbit.zg2uni(getString(R.string.lblbeneficiaryage))
//            edt_beneficiaryfathername.hint = Rabbit.zg2uni(getString(R.string.lblbeneficiaryfthname))
//            lblnrc.text = Rabbit.zg2uni(getString(R.string.lblbeneficiarynrc))
//            tie_nrcno.hint = Rabbit.zg2uni(getString(R.string.lblbeneficiarynrc))
//            edt_beneficiarypercent.hint = Rabbit.zg2uni(getString(R.string.lblbeneficiarypercent))
//            lblfathername.text = Rabbit.zg2uni(getString(R.string.fathername))
//            lblrelationship.text = Rabbit.zg2uni(getString(R.string.lblbeneficiaryrelation))
//            lblpercentage.text = Rabbit.zg2uni(getString(R.string.lblbeneficiarypercent))
//            lblage.text = Rabbit.zg2uni(getString(R.string.lblbeneficiaryage))
//        }else{
//
//            progressDialog.setMessage(getString(R.string.form_dialog))
//        }
        edt_beneficiarypercent.setFilters(arrayOf<android.text.InputFilter>(InputFilter("1", "100")))
        get_Relationships()
        bindRelationships()
        bind_nrcSpinner()

        Log.d(TAG, name+"\n"+dob+"\n"+nrc+"\n"+permanent_addr+"\n"+birth_place+"\n"+gender+"\n"+
                fathername+"\n"+mark+"\n"+height+"\n"+weight+"\n"+officedept+"\n"+occupation+"\n"+salary+"\n"+
                officeId+"\n"+insuranceamt+"\n"+insuranceperiod+"\n"+deposit_amount+"\n"+previous_acceptance_status+"\n"
                +previous_premium_date+"\n"+previous_acceptance_status+"\n"+drug_satus+"\n"+medicial_status+"\n"+userId
        )

//        lifeInsuranceViewModel.entryData.observe(this, androidx.lifecycle.Observer {
//            when(it){
//                is NetWorkViewState.Loading->{
//                    progressDialog.show()
//                }
//                is NetWorkViewState.Success->{
//                    //Log.d(TAG, it.item.insurance_person_id)
//                    progressDialog.dismiss()
//                }
//                is NetWorkViewState.Error->{
//                    Log.d(TAG, it.toString())
//                    progressDialog.dismiss()
//                }
//            }
//        })
        lifeInsuranceViewModel.nrcListData.observe(this, androidx.lifecycle.Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    //clear the existing list and add updated data to list
                    thirdInsuranceLifeActivity.snrcList.clear()
                    thirdInsuranceLifeActivity.snrcHashMap.clear()

                    thirdInsuranceLifeActivity.snrcList.add("ရွေးချယ်ရန်")
                    thirdInsuranceLifeActivity.snrcHashMap.put("ရွေးချယ်ရန်", "0")
                    for(i in 0..it.item.size-1){
                            thirdInsuranceLifeActivity.snrcList.add(it.item.get(i).mm_short_district!!)
                            thirdInsuranceLifeActivity.snrcHashMap.put(it.item.get(i).mm_short_district!!, it.item.get(i).long_district!!)
                    }
                    Log.d(TAG+"result", Gson().toJson(thirdInsuranceLifeActivity.snrcHashMap)+"\n"
                    +Gson().toJson(thirdInsuranceLifeActivity.snrcList))
                    var snrcAdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, thirdInsuranceLifeActivity.snrcList)
                    spn_snrc.adapter = snrcAdapter
                    snrcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    snrcAdapter.notifyDataSetChanged()

                    spn_snrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            secondnrc = thirdInsuranceLifeActivity.snrcHashMap.get(adapterView!!.getItemAtPosition(position).toString())!!
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }

                    progressDialog.hide()

                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }
        })
        btnAddMore.setOnClickListener {
            if (personcontrols_Empty()) {

            } else {
                val addview = getLayoutInflater().inflate(R.layout.beneficiary_listitem, null)
                val lblname_listitem = addview.findViewById<TextView>(R.id.lblbeneficiary_listiem)
                val edtname_listitem =
                    addview.findViewById<EditText>(R.id.edt_beneficiaryname_listiem)
                val lblnrc_listitem = addview.findViewById<TextView>(R.id.lblnrc_listiem)
                var spnfnrc_listitem = addview.findViewById<Spinner>(R.id.spn_fnrc_listiem)
                var spnsnrc_listitem = addview.findViewById<Spinner>(R.id.spn_snrc_listiem)
                var spntnrc_listitem = addview.findViewById<Spinner>(R.id.spn_tnrc_listiem)
                val edtnrc_listitem = addview.findViewById<EditText>(R.id.tie_nrcno_listiem)
                val lblfthname_listitem = addview.findViewById<TextView>(R.id.lblfathername_listiem)
                var edtfthname_listitem =
                    addview.findViewById<EditText>(R.id.edt_beneficiaryfathername_listiem)
                var lblrelationship_listitem =
                    addview.findViewById<TextView>(R.id.lblrelationship_listiem)
                var spnrelationship_listitem =
                    addview.findViewById<Spinner>(R.id.spn_relationship_listiem)
                var lblage_listiem = addview.findViewById<TextView>(R.id.lblage_listiem)
                var edt_beneficiaryage_listiem =
                    addview.findViewById<EditText>(R.id.edt_beneficiaryage_listiem)
                var lblpercentage_listiem =
                    addview.findViewById<TextView>(R.id.lblpercentage_listiem)
                var edt_beneficiarypercent_listiem =
                    addview.findViewById<EditText>(R.id.edt_beneficiarypercent_listiem)
                val btn_delete = addview.findViewById<ImageView>(R.id.btndelete_listiem)
                if (MDetect.isUnicode()) {
                    lblname_listitem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryname)))
                    lblnrc_listitem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiarynrc)))
                    lblfthname_listitem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryfthname)))
                    lblrelationship_listitem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryrelation)))
                    lblage_listiem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryage)))
                    lblpercentage_listiem.setText(Rabbit.zg2uni(getString(R.string.lblbeneficiarypercent)))
                }
                //edtname_listitem.setText(edt_beneficiaryname.text.toString())
                /*
            bind first, second & third spinner item
             */
                var fnrc_no = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    resources.getStringArray(R.array.fnrc)
                )
                fnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnfnrc_listitem.adapter = fnrc_no
                //spnfnrc_listitem.setSelection(spn_fnrc.selectedItemPosition)

                var snrc_no = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    thirdInsuranceLifeActivity.snrcList
                )
                snrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnsnrc_listitem.adapter = snrc_no
                //spnsnrc_listitem.setSelection(spn_snrc.selectedItemPosition)


                var thirdnrcArrayList: ArrayList<String> = arrayListOf()
                if (MDetect.isUnicode()) {
                    for (i in 0..resources.getStringArray(R.array.lastnrc).size - 1) {
                        thirdnrcArrayList.add(
                            Rabbit.zg2uni(
                                resources.getStringArray(R.array.lastnrc).get(
                                    i
                                )
                            )
                        )
                    }
                } else {
                    for (i in 0..resources.getStringArray(R.array.lastnrc).size - 1) {
                        thirdnrcArrayList.add(resources.getStringArray(R.array.lastnrc).get(i))
                    }
                }
                var thirdnrc_no = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    thirdnrcArrayList
                )
                thirdnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spntnrc_listitem.adapter = thirdnrc_no
                //spntnrc_listitem.setSelection(spn_tnrc.selectedItemPosition)

                //edtnrc_listitem.setText(tie_nrcno.text.toString())
                //edtfthname_listitem.setText(edt_beneficiaryfathername.text.toString())
                //txtrelationship.setText(edt_beneficiaryrelationship.text.toString())

                var relationshipAdapter = ArrayAdapter(
                    activity!!,
                    android.R.layout.simple_spinner_item,
                    thirdInsuranceLifeActivity.relationshipList
                )
                relationshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnrelationship_listitem.adapter = relationshipAdapter
                //spnrelationship_listitem.setSelection(spn_relationship.selectedItemPosition)

                //edt_beneficiaryage_listiem.setText(edt_beneficiaryage.text.toString())
                //edt_beneficiarypercent_listiem.setText(edt_beneficiarypercent.text.toString())
                person_container.addView(addview)

                btn_delete.setOnClickListener {
                    Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show()
                    person_container.removeView(it.parent.parent as View)
                }

                //clearTextBoxes()
                edtname_listitem.requestFocus()
                hideKeyboard()
            }
        }
        btnAddAll.setOnClickListener {
            val childCount = person_container.childCount

            for(i in 0..childCount-1){
                val childView:View = person_container.getChildAt(i)
                bname = childView.findViewById<TextView>(R.id.txtbeneficiaryname).text.toString()
                bnrc = childView.findViewById<TextView>(R.id.txtbeneficiarynrc).text.toString()
                fthName = childView.findViewById<TextView>(R.id.txtbeneficiaryfthname).text.toString()
                relationship = childView.findViewById<TextView>(R.id.txtbeneficiaryrelationship).text.toString()
                age = childView.findViewById<TextView>(R.id.txtbeneficiaryage).text.toString().toInt()
                percent = childView.findViewById<TextView>(R.id.txtbeneficiarypercent).text.toString().toDouble()
                bnameList.add(bname)
                bnrcList.add(bnrc)
                fthNameList.add(fthName)
                relationshipList.add(relationship)
                ageList.add(age)
                percentList.add(BeneficiaryPercent(percent))
                beneficiaryList.add(Beneficiarys(bname, bnrc, fthName, relationship, age, percent))
            }
            if(edt_beneficiaryname.text.toString()!="" && tie_nrcno.text.toString()!="" && edt_beneficiaryfathername.text.toString()!=""
                 && edt_beneficiaryage.text.toString()!="" && edt_beneficiarypercent.text.toString()!=""){
                edtbname = edt_beneficiaryname.text.toString()
                edtbnrc = firstnrc+"/"+secondnrc+thirdnrc+tie_nrcno.text.toString()
                edtbfthName = edt_beneficiaryfathername.text.toString()
                edtrelationship = spn_relationship.selectedItem.toString()
                edtage = edt_beneficiaryage.text.toString().toInt()
                edtpercent = edt_beneficiarypercent.text.toString().toDouble()
                bnameList.add(edtbname)
                bnrcList.add(edtbnrc)
                fthNameList.add(edtbfthName)
                relationshipList.add(edtrelationship)
                ageList.add(edtage)
                percentList.add(BeneficiaryPercent(edtpercent))
                beneficiaryList.add(Beneficiarys(edtbname, edtbnrc, edtbfthName, edtrelationship, edtage, edtpercent))
            }

//            }
            Log.d(TAG, Gson().toJson(bnameList))
            Log.d(TAG, Gson().toJson(bnrcList))
            Log.d(TAG, Gson().toJson(fthNameList))
            Log.d(TAG, Gson().toJson(relationshipList))
            Log.d(TAG, Gson().toJson(ageList))
            Log.d(TAG, Gson().toJson(percentList))

            val previewIntent = Intent(activity, PreviewStateActivity::class.java)
            with(previewIntent){
                putExtra("name", name)
                putExtra("dob", dob)
                putExtra("nrc", nrc)
                putExtra("permanent_addr", permanent_addr)
                putExtra("birth_place", birth_place)
                putExtra("gender", gender)
                putExtra("fathername", fathername)
                putExtra("mark", mark)
                putExtra("height", height)
                putExtra("weight", weight)
                //putExtra("officedept", officedept)
                putExtra("occupation", occupation)
                putExtra("salary", salary!!)
                putExtra("officeId", officeId!!)
                putExtra("insuranceamt", insuranceamt!!)
                putExtra("insuranceperiod", insuranceperiod!!)
                putExtra("previous_premium_status", previous_premium_status)
                putExtra("previous_premium_date", previous_premium_date)
                putExtra("previous_acceptance_status", previous_acceptance_status)
                putExtra("drug_satus", drug_satus)
                putExtra("deposit_amount", deposit_amount)
                putStringArrayListExtra("medicial_status", medicial_status)
                putStringArrayListExtra("previewmedicalList", previewmedicalList)
                putExtra("townshipid", townshipid)
                putExtra("departmentid", departmentid)
                putExtra("previewofficename", previewofficename)
                putExtra("previewgender", previewgender)
                putExtra("previewtownship", previewtownship)
                putExtra("previewdepartment", previewdepartment)
                putStringArrayListExtra("bnameList", bnameList)
                putStringArrayListExtra("bnrcList", bnrcList)
                putStringArrayListExtra("fthNameList", fthNameList)
                putStringArrayListExtra("relationshipList", relationshipList)
                putIntegerArrayListExtra("ageList", ageList)
                putExtra("percentList", percentList)
                putExtra("beneficiaryList", beneficiaryList)
                putExtra("userId", userId)
            }
            startActivity(previewIntent)
//            lifeInsuranceViewModel.newEntry(officeId, "0", name!!, gender!!, nrc!!, fathername!!, occupation!!,
//                officedept!!, permanent_addr!!, dob!!, birth_place!!, salary!!, insuranceamt!!, insuranceperiod!!, previous_premium_status,
//                previous_premium_date!!, previous_acceptance_status!!, medicial_status!!, drug_satus!!, mark!!, height!!, weight!!, userId!!,
//                deposit_amount!!, bnameList, bnrcList, fthNameList, relationshipList, ageList, percentList)

        }
        spn_fnrc.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })
        spn_relationship.setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            false
        })

    }
    fun bind_nrcSpinner(){
        /*
        bind main nrc Data 1..14
         */
        var fnrcLength = resources.getStringArray(R.array.fnrc).size
        if(MDetect.isUnicode()){
            thirdInsuranceLifeActivity.mainnrcHashMap.put("ရွေးချယ်ရန်", "0")
        }else{
            thirdInsuranceLifeActivity.mainnrcHashMap.put("ေရြးခ်ယ္ရန္", "0")
        }

        for(i in 0..fnrcLength-1){
            thirdInsuranceLifeActivity.mainnrcHashMap.put(resources.getStringArray(R.array.fnrc).get(i), (i).toString())
        }
        if(MDetect.isUnicode()){
            thirdInsuranceLifeActivity.lastnrcHashMap.put("ရွေးချယ်ရန်", "0")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(နိုင်)", "(N)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(ပြု)", "(P)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(ဧည့်)", "(E)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(သာ)", "(T)")
        }else{
            thirdInsuranceLifeActivity.lastnrcHashMap.put("ေရြးခ်ယ္ရန္", "0")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(ႏိုင္)", "(N)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(ျပဳ)", "(P)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(ဧည့္)", "(E)")
            thirdInsuranceLifeActivity.lastnrcHashMap.put("(သာ)", "(T)")
        }

//        if(MDetect.isUnicode()){
//            for(i in 0..fnrcLength-1){
//                thirdInsuranceLifeActivity.firstArrayList.add(Rabbit.zg2uni(resources.getStringArray(R.array.fnrc).get(i)))
//            }
//        }else{
//            for(i in 0..fnrcLength-1){
//                thirdInsuranceLifeActivity.firstArrayList.addAll(resources.getStringArray(R.array.fnrc))
//            }
//        }
        var fnrc_no = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.fnrc))
        fnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_fnrc.adapter = fnrc_no

//        var subnrc_no = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.subfnrc))
//        spn_snrc.adapter = subnrc_no
//        subnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spn_fnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                progressDialog.show()
                firstnrc = thirdInsuranceLifeActivity.mainnrcHashMap.get(adapterView!!.getItemAtPosition(position)).toString()
                get_nrcById(firstnrc.toInt())
                progressDialog.hide()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        var lastArrayList: ArrayList<String> = arrayListOf()
        if(MDetect.isUnicode()){
            for (i in 0..resources.getStringArray(R.array.lastnrc).size-1){
                thirdInsuranceLifeActivity.lastArrayList.add(Rabbit.zg2uni(resources.getStringArray(R.array.lastnrc).get(i)))
            }
        }else{
            for (i in 0..resources.getStringArray(R.array.lastnrc).size-1){
                thirdInsuranceLifeActivity.lastArrayList.add(resources.getStringArray(R.array.lastnrc).get(i))
            }
        }
        var lastnrc_no = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, thirdInsuranceLifeActivity.lastArrayList)
        lastnrc_no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_tnrc.adapter = lastnrc_no
        spn_tnrc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adpterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                thirdnrc = thirdInsuranceLifeActivity.lastnrcHashMap.get(adpterView!!.getItemAtPosition(position)).toString()
                Log.d(TAG, firstnrc+"/"+secondnrc+thirdnrc)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
    fun get_nrcById(id: Int){
        lifeInsuranceViewModel.getNrcById(id)
    }
    fun clearTextBoxes(){
        edt_beneficiarypercent.setText("")
        edt_beneficiaryage.setText("")
        edt_beneficiaryfathername.setText("")
        tie_nrcno.setText("")
        edt_beneficiaryname.setText("")
        spn_fnrc.setSelection(0)
        spn_tnrc.setSelection(0)
        spn_relationship.setSelection(0)
    }
    fun hideKeyboard(){
        val view = activity!!.findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    fun get_Relationships(){
        lifeInsuranceViewModel.getRelationship()
    }
    fun bindRelationships(){
        /*
        get activity list
         */

        lifeInsuranceViewModel.relationshipData.observe(this, androidx.lifecycle.Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    //clear the existing list and update data
                    relationshipList.clear()
                    thirdInsuranceLifeActivity.relationshipList.add("ရွေးချယ်ရန်")
                    for(i in 0..it.item.size-1){
                        thirdInsuranceLifeActivity.relationshipList.add(it.item.get(i).description!!)
                    }
                    var relationshipdapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, thirdInsuranceLifeActivity.relationshipList)

                    relationshipdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    relationshipdapter.notifyDataSetChanged()
                    spn_relationship.adapter = relationshipdapter
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{

                }
            }
        })

    }

    fun personcontrols_Empty():Boolean{
        if(edt_beneficiaryname.text.toString()=="" && tie_nrcno.text.toString()=="" && edt_beneficiaryfathername.text.toString()==""
            && edt_beneficiaryage.text.toString()=="" && edt_beneficiarypercent.text.toString()==""){
            return true
        }else{
            return false
        }
    }
}