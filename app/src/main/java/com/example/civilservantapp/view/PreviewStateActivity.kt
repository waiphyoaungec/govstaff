package com.example.civilservantapp.view

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.beneficiary.BeneficiaryPercent
import com.example.civilservantapp.model.beneficiary.Beneficiarys
import com.example.civilservantapp.model.beneficiary.OtherBeneficiarys
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_preview_state.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.io.ByteArrayOutputStream
import java.io.File


class PreviewStateActivity : AppCompatActivity() {
    private val lifeInsuranceViewModel: LifeInsuranceViewModel by lazy { ViewModelProviders.of(this).get(
        LifeInsuranceViewModel::class.java) }

    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProviders.of(this).get(FetchDataViewModel::class.java)
    }
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
    var doublepercentList: ArrayList<Double> = arrayListOf()
    var beneficiaryList: ArrayList<Beneficiarys> = arrayListOf()
    var userId: String?=""
    var previous_policy_id: String=""
    var health_recommended_letter: String?=""
    var bitmaptype: String? = ""
    var amount: Int?=0
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
    private var age_next_year: Int =0
    private var phone_no: String? = ""
    private var military = 0
    /*
    for Second Form
     */
    private var previous_premium_status: Int =0
    private var previous_premium_date: String=""
    private var previous_acceptance_status: Int =0
    private var drug_satus: Int=0
    private var medicial_status: ArrayList<Int> = arrayListOf()
    /*
preview information data
*/
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    private var previewmedicalList: ArrayList<String> = arrayListOf()
    var otherfactsList: ArrayList<String> = arrayListOf()
    var otherpercentsList: ArrayList<BeneficiaryPercent> = arrayListOf()
    var otherBeneficiaryList: ArrayList<OtherBeneficiarys> = arrayListOf()
    var dblotherpercentsList: ArrayList<Double> = arrayListOf()
    var paymenttype:String?=""

    /*
    var confirm
     */
    lateinit var progressDialog: ProgressDialog

    var confirmname: String?=""
    var confirmpolicyid: String?=""
    var confirminvoiceid: String?=""
    var confirmsubmitedmonth: String?=""
    var confirmpaymentytpe: String?=""
    var confirmamount: Int?=0

    lateinit var sharedPreferences: SharedPreference
    var getfilepath: String?=""
    var extension: String?=""
    var base64string: String = ""

    var bank: Int? = 0
    var easy_pay: Int?=0
    var sh: Int? = 0
    var insurance_person_id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_state)
        deleteFile()
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        sharedPreferences = SharedPreference(this)
        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                //get intent with data
                /*
                for first
                 */
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
                health_recommended_letter = getString("health_recommended_letter")
                bitmaptype = getString("bitmaptype")
                amount = getInt("amount")
                phone_no = getString("phone_no")

                /*
                for second
                 */
                previous_premium_status = bundle.get("previous_premium_status") as Int
                previous_premium_date = getString("previous_premium_date")!!
                previous_acceptance_status = bundle.get("previous_acceptance_status") as Int
                drug_satus = bundle.get("drug_satus") as Int
                medicial_status = getIntegerArrayList("medicial_status")!!
                previewmedicalList = getStringArrayList("previewmedicalList")!!

                /*
                for third form
                 */
                userId = getString("userId")
                previous_policy_id = getString("previous_policy_id")!!
                bnameList = bundle.getStringArrayList("bnameList")!!
                bnrcList = bundle.getStringArrayList("bnrcList")!!
                fthNameList = bundle.getStringArrayList("fthNameList")!!
                relationshipList = bundle.getStringArrayList("relationshipList")!!
                ageList = bundle.getIntegerArrayList("ageList")!!
                percentList = intent.getSerializableExtra("percentList") as ArrayList<BeneficiaryPercent>
                beneficiaryList = intent.getSerializableExtra("beneficiaryList") as ArrayList<Beneficiarys>
                otherfactsList = intent.getSerializableExtra("other_beneficiary_description") as ArrayList<String>
                otherpercentsList = intent.getSerializableExtra("other_beneficiary_percent") as ArrayList<BeneficiaryPercent>
                otherBeneficiaryList = intent.getSerializableExtra("otherbeneficiaryList") as ArrayList<OtherBeneficiarys>
            Log.d("PreviewBeneficiaryInfo", Gson().toJson(bnameList)+"\n"+Gson().toJson(bnrcList)+"\n"
            +Gson().toJson(fthNameList)+"\n"+Gson().toJson(relationshipList)+"\n"+Gson().toJson(ageList)+"\n"+Gson().toJson(percentList)+
            Gson().toJson(otherfactsList)+"\n"+Gson().toJson(otherpercentsList)+userId)

            }
        }

//        if(MDetect.isUnicode()){
//            progressDialog.setMessage(Rabbit.zg2uni(getString(R.string.form_dialog)))
//            toolbar.title = Rabbit.zg2uni(getString(R.string.life_titlepreview))
//            toolbar.setTitleTextColor(getColor(R.color.btnforeColor))
//            lblpreviewofficename.setText(Rabbit.zg2uni(getString(R.string.preview_officename)))
//            lblpreviewname.setText(Rabbit.zg2uni(getString(R.string.servname)))
//            lblpreviewgender.setText(Rabbit.zg2uni(getString(R.string.preview_gender)))
//            lblpreviewnrc.setText(Rabbit.zg2uni(getString(R.string.nrc_lbl)))
//            lblpreviewfthname.setText(Rabbit.zg2uni(getString(R.string.fathername)))
//            lblpreviewfulljob.setText(Rabbit.zg2uni(getString(R.string.fulljob)))
//            lbltownship.setText(Rabbit.zg2uni(getString(R.string.preview_township)))
//            lbldepartment.setText(Rabbit.zg2uni(getString(R.string.preview_department)))
//            lblpermanentaddr.setText(Rabbit.zg2uni(getString(R.string.preview_address)))
//            lbldob.setText(Rabbit.zg2uni(getString(R.string.serdob)))
//            //lblcommingage.setText(Rabbit.zg2uni(getString(R.string.commingage)))
//            lblsalary.setText(Rabbit.zg2uni(getString(R.string.salary)))
//            lbliamount.setText(Rabbit.zg2uni(getString(R.string.iamount)))
//            lblilength.setText(Rabbit.zg2uni(getString(R.string.ilength)))
//            lblweight.setText(Rabbit.zg2uni(getString(R.string.lblweight)))
//            lblheight.setText(Rabbit.zg2uni(getString(R.string.lblheight)))
//            lblpremiumkyat.setText(Rabbit.zg2uni(getString(R.string.premium_kyat)))
//            lblbodysign.setText(Rabbit.zg2uni(getString(R.string.lblbodysign)))
//            txtpreviewdiseasetitle.setText(Rabbit.zg2uni(getString(R.string.lblhappendisease)))
//            //lblcost.setText(Rabbit.zg2uni(getString(R.string.cost)))
//            btnconfirmsave.setText(Rabbit.zg2uni(getString(R.string.btn_preview)))
//            txtpreview_beneficiaryperson.setText("အကျိုးခံစားခွင့်ရှိသူ")
//            txtpreview_beneficiaryother.setText("အခြားအကျိုးခံစားခွင့်ရှိသူ")
//        }else{
//            toolbar.title = getString(R.string.life_titlepreview)
//            toolbar.setTitleTextColor(getColor(R.color.btnforeColor))
//            progressDialog.setMessage(getString(R.string.form_dialog))
//        }
        txtpreviewofficename.setText(previewofficename)
        txtpreviewname.setText(name)
        txtpreviewgender.setText(previewgender)
        txtpreviewnrc.setText(nrc)
        txtpreviewfthname.setText(fathername)
        txtpreviewoccupation.setText(occupation)
        txtpreviewtownship.setText(previewtownship)
        txtpreviewdepartment.setText(previewdepartment)
        txtpermanentaddress.setText(permanent_addr)
        txtpreviewdob.setText(dob)
        //txtpreviewcommingage.setText("")
        txtpreviewsalary.setText(salary.toString())
        txtpreviewiamount.setText(insuranceamt.toString())
        txtpremiumkyat.setText("0")
        txtpreviewilength.setText(insuranceperiod.toString())
        txtpreviewbodysign.setText(mark)
        txtpreviewheight.setText(height)
        txtpreviewweight.setText(weight)
        txtpremiumkyat.setText(deposit_amount.toString())
        txtpreviewphno.setText(phone_no)
        if(military == 1){
            soldiertextview.setText("တပ်မတော်သား")
        }
        else{
            soldiertextview.setText("အခြား")
        }

        if(previous_premium_status.equals(1)){
            if(MDetect.isUnicode()){
                txtpreviewpassexist.setText(Rabbit.zg2uni(getString(R.string.preview_previousformy)))
                img_passexist.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }else{
                txtpreviewpassexist.setText(getString(R.string.preview_previousformy))
                img_passexist.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }
        }else{
            if(MDetect.isUnicode()){
                txtpreviewpassexist.setText(Rabbit.zg2uni(getString(R.string.preview_previousforn)))
                img_passexist.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }else{
                txtpreviewpassexist.setText(getString(R.string.preview_previousforn))
                img_passexist.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }
        }
        if(previous_acceptance_status.equals(1)){
            if(MDetect.isUnicode()){
                txtpreviewacceptorreject.setText(Rabbit.zg2uni(getString(R.string.preview_accept)))
                img_acceptorreject.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }else{
                txtpreviewacceptorreject.setText(getString(R.string.preview_accept))
                img_acceptorreject.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }
        }else{
            if(MDetect.isUnicode()){
                txtpreviewacceptorreject.setText(Rabbit.zg2uni(getString(R.string.preview_reject)))
                img_acceptorreject.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }else{
                txtpreviewacceptorreject.setText(getString(R.string.preview_reject))
                img_acceptorreject.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }
        }

        if(drug_satus.equals(1)){
            if(MDetect.isUnicode()){
                txtpreviewdrug.setText(Rabbit.zg2uni(getString(R.string.preview_drugsy)))
                img_previewdrug.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }else{
                txtpreviewdrug.setText(getString(R.string.preview_drugsy))
                img_previewdrug.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_true))
            }
        }else{
            if(MDetect.isUnicode()){
                txtpreviewdrug.setText(Rabbit.zg2uni(getString(R.string.preview_drugsn)))
                img_previewdrug.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }else{
                txtpreviewdrug.setText(getString(R.string.preview_drugsn))
                img_previewdrug.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.img_false))
            }
        }
        if(previewmedicalList.size==0){
            txtpreviewdiseasetitle.visibility= View.GONE
        }else{
            txtpreviewdiseasetitle.visibility=View.VISIBLE
        }
        for(i in 0..previewmedicalList.size-1){
            txtpreviewdiseaselist.append(previewmedicalList.get(i)+"\n")
        }

        val table = this.findViewById(R.id.beneficiarytable) as TableLayout
        val row = LayoutInflater.from(this).inflate(R.layout.beneficiaryrows,
            null
        ) as TableRow
        if(MDetect.isUnicode()){
            (row.findViewById(R.id.tvbeneficiaryname) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryname)))
            (row.findViewById(R.id.tvbeneficiarynrc) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiarynrc)))
            (row.findViewById(R.id.tvbeneficiaryfthname) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryfthname)))
            (row.findViewById(R.id.tvbeneficiaryrelationship) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryrelation)))
            (row.findViewById(R.id.tvbeneficiaryage) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiaryage)))
            (row.findViewById(R.id.tvbeneficiarypercent) as TextView).setText(Rabbit.zg2uni(getString(R.string.lblbeneficiarypercent)))
        }else{
            (row.findViewById(R.id.tvbeneficiaryname) as TextView).setText(getString(R.string.lblbeneficiaryname))
            (row.findViewById(R.id.tvbeneficiarynrc) as TextView).setText(getString(R.string.lblbeneficiarynrc))
            (row.findViewById(R.id.tvbeneficiaryfthname) as TextView).setText(R.string.lblbeneficiaryfthname)
            (row.findViewById(R.id.tvbeneficiaryrelationship) as TextView).setText(R.string.lblbeneficiaryrelation)
            (row.findViewById(R.id.tvbeneficiaryage) as TextView).setText(R.string.lblbeneficiaryage)
            (row.findViewById(R.id.tvbeneficiarypercent) as TextView).setText(R.string.lblbeneficiarypercent)
        }

        table.addView(row)
        if(beneficiaryList.size==0){
            txtpreview_beneficiaryperson.visibility=View.GONE
            beneficiarytable.visibility=View.GONE
        }else{
            txtpreview_beneficiaryperson.visibility=View.VISIBLE
            beneficiarytable.visibility=View.VISIBLE
        }
        if(otherBeneficiaryList.size==0){
            txtpreview_beneficiaryother.visibility=View.GONE
            beneficiary_othertable.visibility=View.GONE
        }else{
            txtpreview_beneficiaryother.visibility=View.VISIBLE
            beneficiary_othertable.visibility=View.VISIBLE
        }
        for (i in 0..beneficiaryList.size-1) {
            // Inflate your row "template" and fill out the fields.
            val row = LayoutInflater.from(this).inflate(R.layout.beneficiaryrows,
                null
            ) as TableRow
            (row.findViewById(R.id.tvbeneficiaryname) as TextView).setText(beneficiaryList.get(i).name)
            (row.findViewById(R.id.tvbeneficiarynrc) as TextView).setText(beneficiaryList.get(i).nrc)
            (row.findViewById(R.id.tvbeneficiaryfthname) as TextView).setText(beneficiaryList.get(i).fathername)
            (row.findViewById(R.id.tvbeneficiaryrelationship) as TextView).setText(beneficiaryList.get(i).relationship)
            (row.findViewById(R.id.tvbeneficiaryage) as TextView).setText(beneficiaryList.get(i).age.toString())
            (row.findViewById(R.id.tvbeneficiarypercent) as TextView).setText(beneficiaryList.get(i).percentage.toString())
            table.addView(row)
        }
        table.requestLayout()
        val othertable = this.findViewById<TableLayout>(R.id.beneficiary_othertable)
        val otherrow = LayoutInflater.from(this).inflate(R.layout.otherbeneficiaryrows, null) as TableRow

            (otherrow.findViewById<TextView>(R.id.tvbeneficiaryfacts)).setText("အကြောင်းအရာ")
            (otherrow.findViewById<TextView>(R.id.tvbeneficiarypercent)).setText("ရာခိုင်နှုန်းအချိုးအစား")
        othertable.addView(otherrow)
        for(i in 0..otherBeneficiaryList.size-1){
            val otherrow = LayoutInflater.from(this).inflate(R.layout.otherbeneficiaryrows, null) as TableRow
            (otherrow.findViewById<TextView>(R.id.tvbeneficiaryfacts)).setText(otherBeneficiaryList.get(i).facts)
            (otherrow.findViewById<TextView>(R.id.tvbeneficiarypercent)).setText(otherBeneficiaryList.get(i).percents.toString())
            othertable.addView(otherrow)
        }

        othertable.requestLayout()


        //txtcost.setText(deposit_amount.toString())
        btnconfirmsave.setOnClickListener {
            // create an alert builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle("")
            // set the custom layout
            val customLayout = layoutInflater.inflate(R.layout.overthecounter_layout, null)
            builder.setView(customLayout)
            val lblconfirmamount = customLayout.findViewById<TextView>(R.id.lblconfirmamount)
            val lblpremiumkyat = customLayout.findViewById<TextView>(R.id.lblpremiumkyat)
            val chkoverthecounter = customLayout.findViewById<RadioButton>(R.id.chkoverthecounter)
            val chkmobilepay = customLayout.findViewById<RadioButton>(R.id.chkmobilewallet)
            if(MDetect.isUnicode()){
                lblconfirmamount.setText("ကျသင့်ငွေ")
            }
            lblpremiumkyat.setText(amount.toString())
            // add a button
            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    // send data from the AlertDialog to the Activity

                    if(chkoverthecounter.isChecked){
                        paymenttype = "otc"
                    }else{
                        paymenttype = "easypay"
                    }

                    for(i in 0..percentList.size-1){
                        doublepercentList.add(percentList.get(i).percent!!)
                    }
                        for(i in 0..otherpercentsList.size-1){
                            dblotherpercentsList.add(otherpercentsList.get(i).percent!!)
                        }

                    getfilepath = sharedPreferences.getUserInfo("health_recommended_letter")
                    /*
                    for temporary
                     */

                    extension = sharedPreferences.getUserInfo("extension")
                    if(getfilepath!=""){
                        val resizeBitmap = BitmapFactory.decodeFile(getfilepath)

                        if(resizeBitmap!=null){
                            base64string = bitmapToBase64(resizeBitmap)
                            Log.d("Preview", base64string+"\n"+extension+age_next_year)
                        }
                    }
                    if(paymenttype.equals("easypay")){
                        fetchDataViewModel.getFee()
                    }else if(paymenttype.equals("otc")){
                        lifeInsuranceViewModel.newEntry(military,officeId, paymenttype!!, name!!, gender!!, nrc!!, fathername!!, occupation!!,
                            deposit_amount!!, townshipid!!, departmentid!!, permanent_addr!!, dob!!, birth_place!!, salary!!, insuranceamt!!,
                            insuranceperiod!!, previous_premium_status!!,
                            previous_acceptance_status!!, medicial_status!!, drug_satus!!, mark!!, height!!, weight!!, userId!!,
                            amount!!, bnameList, bnrcList, fthNameList, relationshipList, ageList, doublepercentList, otherfactsList,
                            dblotherpercentsList, phone_no!!, previous_policy_id, age_next_year, base64string, extension, null, null, null)
                    }
                })
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()

        }
        lifeInsuranceViewModel.entryData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    Log.d("PreviewEntry", it!!.toString())
                    progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    Log.d("test","Save Successfully")
                    insurance_person_id = it.item.insurance_person_id
                    confirmname = it.item.newinvoice.name
                    confirmpolicyid = it.item.newinvoice.policy_id
                    confirminvoiceid = it.item.newinvoice.invoice_num
                    confirmsubmitedmonth = it.item.newinvoice.created_date
                    confirmpaymentytpe = "Mobile Wallet Payment"
                    confirmamount = it.item.newinvoice.amount   !!
                    sharedPreferences.saveUserInfo("policy_id", it.item.newinvoice.policy_id!!)
                    lifeInsuranceViewModel.MatchUserPolicyId(userId!!.toInt(), it.item.newinvoice.policy_id!!)
                    progressDialog.hide()
                }
            }
        })

        lifeInsuranceViewModel.matchData.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()
                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    if(it.item.message.equals("true")){
                        deleteFile()
                        val confirmpaymentIntent = Intent(this, ConfirmPaymentActivity::class.java)
                        with(confirmpaymentIntent){
                            putExtra("insurance_person_id", insurance_person_id)
                            putExtra("name", confirmname)
                            putExtra("policyid", confirmpolicyid)
                            putExtra("invoiceid", confirminvoiceid)
                            putExtra("submitedmonth", confirmsubmitedmonth)
                            putExtra("amount", confirmamount)
                            putExtra("paymenttype", paymenttype)
                        }

                        deleteFile()
                        sharedPreferences.saveUserInfo("health_recommended_letter", "")
                        startActivity(confirmpaymentIntent)
                    }
                    else if(it.item.message.equals("false")){
                        Toast.makeText(applicationContext,"Somethings Wrong",Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }
            }
        })

        fetchDataViewModel.getFee.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressDialog.show()

                }
                is NetWorkViewState.Error->{
                    progressDialog.hide()
                }
                is NetWorkViewState.Success->{
                    bank = it.item.bank
                    easy_pay = it.item.easy_pay
                    sh = it.item.sh
                    Log.d("Preview_getFee", bank.toString()+"\n"+easy_pay.toString()+"\n"+sh.toString())
                    if(bank!!>0 && easy_pay!!>0 && sh!!>0){
                        Log.d("PreviewInsert", bank!!.toString()+"\n"+easy_pay.toString()+"\n"+sh.toString())
                        try {
                            Log.d("PreviewSave", Gson().toJson(bnameList)+"\n"+Gson().toJson(bnrcList)+"\n"
                                    +Gson().toJson(fthNameList)+"\n"+Gson().toJson(relationshipList)+"\n"+Gson().toJson(ageList)+"\n"+
                                    Gson().toJson(doublepercentList)+ Gson().toJson(otherfactsList)+"\n"+Gson().toJson(dblotherpercentsList)+
                                    "\n"+deposit_amount+"\n"+amount)

                            lifeInsuranceViewModel.newEntry(military,officeId, paymenttype!!, name!!, gender!!, nrc!!, fathername!!, occupation!!,
                                deposit_amount!!, townshipid!!, departmentid!!, permanent_addr!!, dob!!, birth_place!!, salary!!, insuranceamt!!,
                                insuranceperiod!!, previous_premium_status!!,
                                previous_acceptance_status!!, medicial_status!!, drug_satus!!, mark!!, height!!, weight!!, userId!!,
                                amount!!, bnameList, bnrcList, fthNameList, relationshipList, ageList, doublepercentList, otherfactsList,
                                dblotherpercentsList, phone_no!!, previous_policy_id, age_next_year, base64string, extension, bank, easy_pay, sh)
                        }catch (e: Exception){
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    progressDialog.hide()
                }
            }
        })
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(applicationContext,R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun deleteFile(){
        if(getfilepath!=null){
            Log.d("Preview", getfilepath)
            val fdelete = File(getfilepath)
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    Log.d("Preview" , "file Deleted :"+getfilepath)
                } else {
                    Log.d("Preview" , "file not Deleted :"+getfilepath)
                }
            }
        }

    }
}
