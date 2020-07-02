package com.example.civilservantapp.view

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.adapter.DiseaseListAdapter
import com.example.civilservantapp.model.Disease
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import me.myatminsoe.mdetect.MDetect
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.civilservantapp.model.NetWorkViewState
import kotlinx.android.synthetic.main.activity_lifeinsurance_second.*
import me.myatminsoe.mdetect.Rabbit
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.civilservantapp.view.activity.ThirdInsuranceLifeActivity
import com.google.gson.Gson


class LifeinsuranceSecond : AppCompatActivity() {
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(LifeInsuranceViewModel::class.java) }
    private var TAG="LifeInsuranceSecond"
    private var diseaseList: ArrayList<Disease> = arrayListOf()
    private var diseaseListAdapter : DiseaseListAdapter = DiseaseListAdapter(diseaseList)
    var health_recommended_letter: String?=""
    var bitmaptype: String? = ""
    /*
    variable for second
     */
    private var previous_premium_status: Int =0
    private var previous_premium_date: String?=""
    private var previous_acceptance_status: Int =0
    private var drug_satus: Int=0
    private var medicial_status: ArrayList<String> = arrayListOf()
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
    private var age_next_year: Int=0
    private var amount: Int?=0
    private var phone_no: String?=""
    private var military = 0

    /*
  preview information data
   */
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    private var previewmedicalList: ArrayList<String> = arrayListOf()

    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifeinsurance_second)
        MDetect.init(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        var bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                military = getInt("soldier")
                name = getString("name")
                dob = getString("dob")
                nrc = getString("nrc")
                permanent_addr = getString("permanent_addr")
                birth_place = getString("birth_place")
                gender = getString("gender")
                fathername = getString("fathername")
                mark = getString("mark")
                height = getString("height")
                weight = getString("weight")
                //officedept = getString("officedept")
                occupation = getString("occupation")
                salary = bundle.get("salary") as Int
                officeId = bundle.get("officeId") as Int
                insuranceamt = bundle.get("insuranceamt") as Int
                insuranceperiod = bundle.get("insuranceperiod") as Int
                deposit_amount = bundle.get("deposit_amount") as Int
                townshipid = bundle.get("townshipid") as Int
                departmentid = bundle.get("departmentid") as Int
                previewofficename = getString("previewofficename")
                previewgender = getString("previewgender")
                previewtownship = getString("previewtownship")
                previewdepartment = getString("previewdepartment")
                age_next_year = getInt("age_next_year")
                amount = getInt("amount")
                phone_no = getString("phone_no")

                Log.d(TAG, name+"\n"+dob+"\n"+nrc+"\n"+permanent_addr+"\n"+birth_place+"\n"+gender+"\n"+fathername+"\n"
                +mark+"\n"+height+"\n"+weight+"\n"+officedept+"\t"+occupation+"\n"+salary+"\n"+officeId+"\n"+insuranceamt+"\n"
                +insuranceperiod+"\n"+deposit_amount+"\n"+age_next_year+"\n"+amount+"\n"+phone_no)
            }
        }
        if(MDetect.isUnicode()){
            progressDialog.setMessage(Rabbit.zg2uni(getString(R.string.form_dialog)))
            toolbar.title = Rabbit.zg2uni(getString(R.string.lifeTitle))
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
            chkpassexist.setText(Rabbit.zg2uni(getString(R.string.lblpassexist)))
            chkacceptreject.setText(Rabbit.zg2uni(getString(R.string.lblacceptorreject)))
            chkanydrag.setText(Rabbit.zg2uni(getString(R.string.lblanydrug)))
            lbldiseaselist.setText(Rabbit.zg2uni(getString(R.string.lbldiseaselist)))
        }else{
            toolbar.title = getString(R.string.lifeTitle)
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
            progressDialog.setMessage(getString(R.string.form_dialog))
        }
        lifeInsuranceViewModel.getMedicalList()
        val recyclerLayoutManager = LinearLayoutManager(this)
        recyclerdiseaseList.layoutManager = recyclerLayoutManager
        recyclerdiseaseList.adapter = diseaseListAdapter
        lifeInsuranceViewModel.medicalList.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Success->{
                    diseaseList = it.item
                    diseaseListAdapter = DiseaseListAdapter(diseaseList)
                    diseaseListAdapter.notifyDataSetChanged()
                    recyclerdiseaseList.adapter = diseaseListAdapter
                    progressDialog.hide()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
            }

        })

            radiopassexist.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                        Log.d("chk", "id$checkedId")
                        if (checkedId == R.id.rdbtnexist) {
                            previous_premium_status = 1
                            edt_previous_policyid.visibility= View.VISIBLE
                        } else if (checkedId == R.id.rdbtnnotexist) {
                            previous_premium_status = 0
                            edt_previous_policyid.visibility=View.GONE
                        }

                }

            })
        radioacceptrej.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if(checkedId==R.id.rdbtnaccept){
                    previous_acceptance_status =1
                }else if(checkedId==R.id.rdbtnrej){
                    previous_acceptance_status = 0
                }
            }
        })

        radiodrugs.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if(checkedId==R.id.rdbtnacceptdrug){
                    drug_satus = 1
                }else if(checkedId == R.id.rdbtnrejdrug){
                    drug_satus = 0
                }
            }
        })


        btnsave_sinsurance.setOnClickListener {
            var getotherdisease: String=""
            var getotherdiseasestatus: Boolean  =false
            try {

                    if(rdbtnexist.isChecked){
                        previous_premium_status = 1
                        previous_premium_date = edt_previous_policyid.text.toString()
                    }else{
                        previous_premium_status = 0
                    }
                    if(rdbtnaccept.isChecked){
                        previous_acceptance_status=1
                    }else{
                        previous_acceptance_status =0
                    }
                    if(rdbtnacceptdrug.isChecked){
                        drug_satus = 1
                    }else{
                        drug_satus =0
                    }

                    var selectedRows = diseaseListAdapter.getSelectedIds()
                Log.d(TAG, "selectedrows"+Gson().toJson(selectedRows)+"\t"+selectedRows.size().toString())
                    if(selectedRows.size()>0){
                        for (i in 0..selectedRows.size()-1){
                            if(selectedRows.keyAt(i)==15){
                                medicial_status.add(diseaseList.get(selectedRows.keyAt(i)).id.toString())
                                var view = recyclerdiseaseList.findViewHolderForLayoutPosition(15) as DiseaseListAdapter.ViewHolder
                                getotherdisease = view.edtotherdisease.text.toString()
                                previewmedicalList.add(getotherdisease)
                                getotherdiseasestatus = true
                                Log.d(TAG, "otherdisease"+ getotherdisease+"\t"+getotherdiseasestatus)
                            }else{
                                medicial_status.add(diseaseList.get(selectedRows.keyAt(i)).id.toString())
                                previewmedicalList.add(diseaseList.get(selectedRows.keyAt(i)).description!!)
                            }

                        }
                    }
                    if(getotherdisease.equals("") && getotherdiseasestatus == true){
                        Toast.makeText(this, "အခြားသောရောဂါများကိုထည့်ပါ", Toast.LENGTH_SHORT).show()
                    }else {
                        if (previous_premium_date.equals("") && previous_premium_status.equals(1)) {
                            Toast.makeText(this, "ယခင်ပေါ်လစီအမှတ်ကိုထည့်ပါ", Toast.LENGTH_SHORT)
                                .show()
                        } else {

                            val thirdIntent = Intent(this, ThirdInsuranceLifeActivity::class.java)
                            for (i in 0..previewmedicalList.size - 1) {
                                Log.d(TAG, previewmedicalList.get(i))
                            }
                            with(thirdIntent) {
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
                                putExtra("previous_policy_id", "")
                                putExtra("age_next_year", age_next_year)
                                putExtra("amount", amount)
                                putExtra("phone_no", phone_no)
                                putExtra("soldier",military)

                            }
                            startActivity(thirdIntent)
                        }
                    }


            }catch (e: Exception){

            }

            Log.d(TAG, previous_premium_status.toString()+"\n"+previous_premium_date+"\n"+previous_acceptance_status+"\n"+drug_satus+"\n"+medicial_status.size)
        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
