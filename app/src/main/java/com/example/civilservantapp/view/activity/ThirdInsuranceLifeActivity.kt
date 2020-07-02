package com.example.civilservantapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.civilservantapp.R
import com.example.civilservantapp.adapter.MyPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.beneficiary.BeneficiaryPercent
import com.example.civilservantapp.model.beneficiary.Beneficiarys
import com.example.civilservantapp.model.beneficiary.OtherBeneficiarys
import com.example.civilservantapp.view.PreviewStateActivity
import com.example.civilservantapp.view.fragment.PersonFragment
import com.example.civilservantapp.viewmodel.LifeInsuranceViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_third_insurance_life.*
import kotlinx.android.synthetic.main.fragment_person.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.HashMap


class ThirdInsuranceLifeActivity : AppCompatActivity() {
   lateinit var vpPager: ViewPager
    lateinit var adapterViewPager: MyPagerAdapter
    lateinit var progressDialog: ProgressDialog
    var health_recommended_letter: String?=""
    var bitmaptype: String? = ""
    var resizedBitmap: Bitmap?=null
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
    private var amount: Int?=0
    private var phone_no: String?=""
    /*
    for Second Form
     */
    private var previous_premium_status: Int =0
    private var previous_premium_date: String=""
    private var previous_acceptance_status: Int =0
    private var drug_satus: Int=0
    private var medicial_status: ArrayList<String> = arrayListOf()
    var previous_policy_id:String?=""

    lateinit var sharedPreference: SharedPreference

    /*
preview information data
 */
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    private var previewmedicalList: ArrayList<String> = arrayListOf()

    var firstnrc:String=""
    var secondnrc:String=""
    var thirdnrc:String=""
    var nrcno:String=""
    /*
    uper layout control for person
     */
    var edtbname: String=""
    var bfirstnrc: String=""
    var bsecondnrc: String=""
    var bthirdnrc: String=""
    var bnrcno: String=""
    var edtbnrc: String=""
    var edtbfthName: String=""
    var brelationship: String=""
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
    var mainRelationshipList: ArrayList<String> = arrayListOf()
    var ageList: ArrayList<Int> = arrayListOf()
    var percentList: ArrayList<BeneficiaryPercent> = arrayListOf()
    var userId: String?=""
    var phno: String?=""
    var beneficiaryList: ArrayList<Beneficiarys> = arrayListOf()
    var otherBeneficiaryList: ArrayList<OtherBeneficiarys> = arrayListOf()


    var personHeader:String=""
    var otherHeader:String=""
    /*
    Preson controls
     */
    var snrcList : ArrayList<String> = arrayListOf()
    var snrcHashMap: HashMap<String, String> = HashMap()
    var mainnrcHashMap : HashMap<String, String> = HashMap()
    var lastnrcHashMap: HashMap<String, String> = HashMap()
    var firstArrayList: ArrayList<String> = arrayListOf()
    var lastArrayList: ArrayList<String> = arrayListOf()
    private var military = 0

    /*
    other form data and control
     */
    var otherfacts: String = ""
    var otherpercents: Double = 0.0
    var edtotherfacts: String=""
    var edtotherpercents: Double = 0.0
    var otherfactsList: ArrayList<String> = arrayListOf()
    var otherpercentsList: ArrayList<BeneficiaryPercent> = arrayListOf()
    private var age_next_year: Int=0

    private var totalAmt : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_insurance_life)
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        sharedPreference = SharedPreference(this)
        MDetect.init(this)
        if(MDetect.isUnicode()){
            toolbar.setTitle("အသက်အာမခံ အဆိုလွှာ")
        }
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(applicationContext,R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            finish()
        }
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
                amount = getInt("amount")
                phone_no = getString("phone_no")

                /*
                for second
                 */
                previous_premium_status = bundle.get("previous_premium_status") as Int
                previous_premium_date = getString("previous_premium_date")!!
                previous_acceptance_status = bundle.get("drug_satus") as Int
                drug_satus = bundle.get("drug_satus") as Int
                medicial_status = getStringArrayList("medicial_status")!!
                previewmedicalList = getStringArrayList("previewmedicalList")!!
                previous_policy_id = getString("previous_policy_id")!!

                /*
                for third
                 */
                userId = sharedPreference.getUserInfo("user_id").toString()
                phno = sharedPreference.getUserInfo("phone").toString()
                Log.d("Thirdfetch", "policyid"+previous_policy_id+"\n"+userId+"\n"+phno)
            }

        }

        if(MDetect.isUnicode()){
            personHeader = Rabbit.zg2uni(getString(R.string.title_personbeneficiary))
            otherHeader = Rabbit.zg2uni(getString(R.string.title_otherbeneficiary))
        }else{
            personHeader = getString(R.string.title_personbeneficiary)
            otherHeader = getString(R.string.title_otherbeneficiary)
        }
        vpPager = findViewById(R.id.viewpager) as ViewPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager, personHeader, otherHeader)
        vpPager.adapter = adapterViewPager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.beneficiary_menuitem, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==R.id.action_addall){
            var personFragment = adapterViewPager.getItem(0) as PersonFragment



            var otherfacts = viewpager.findViewById<EditText>(R.id.edt_otherfacts).text.toString()
            var container = viewpager.findViewById<LinearLayout>(R.id.person_container)
            val childCount = container.childCount
            edtbname = viewpager.findViewById<EditText>(R.id.edt_beneficiaryname).text.toString()
            bfirstnrc = mainnrcHashMap.get(viewpager.findViewById<Spinner>(R.id.spn_fnrc).selectedItem)!!
            bsecondnrc = snrcHashMap.get(viewpager.findViewById<Spinner>(R.id.spn_snrc).selectedItem)!!
            bthirdnrc = lastnrcHashMap.get(viewpager.findViewById<Spinner>(R.id.spn_tnrc).selectedItem)!!
            edtbnrc = viewpager.findViewById<EditText>(R.id.tie_nrcno).text.toString()
            bnrcno = bfirstnrc+"/"+bsecondnrc+bthirdnrc+edtbnrc
            edtbfthName = viewpager.findViewById<EditText>(R.id.edt_beneficiaryfathername).text.toString()
            brelationship = viewpager.findViewById<Spinner>(R.id.spn_relationship).selectedItem.toString()
            if(viewpager.findViewById<EditText>(R.id.edt_beneficiaryage).text.toString() == ""){
                edtage = 0
            }else{
                edtage = viewpager.findViewById<EditText>(R.id.edt_beneficiaryage).text.toString().toInt()
            }
            if(viewpager.findViewById<EditText>(R.id.edt_beneficiarypercent).text.toString() == ""){
                edtpercent = 0.0
            }else{
                edtpercent = viewpager.findViewById<EditText>(R.id.edt_beneficiarypercent).text.toString().toDouble()
            }


            if(edtbname!="" && edtbfthName!="" && brelationship!="" && edtage!=0 && edtpercent!=0.0){
                bnameList.add(edtbname)
                bnrcList.add(bnrcno)
                fthNameList.add(edtbfthName)
                mainRelationshipList.add(brelationship)
                ageList.add(edtage)
                percentList.add(BeneficiaryPercent(edtpercent))
                beneficiaryList.add(Beneficiarys(edtbname, bnrcno, edtbfthName, brelationship, edtage, edtpercent))
            }
            Log.d("FormThird", Gson().toJson(mainRelationshipList))

            try{
                for(i in 0..childCount-1){
                    val childView: View = container.getChildAt(i)
                    bname = childView.findViewById<TextView>(R.id.edt_beneficiaryname_listiem).text.toString()
                    firstnrc = mainnrcHashMap.get(childView.findViewById<Spinner>(R.id.spn_fnrc_listiem).selectedItem)!!
                    secondnrc = snrcHashMap.get(childView.findViewById<Spinner>(R.id.spn_snrc_listiem).selectedItem)!!
                    thirdnrc = lastnrcHashMap.get(childView.findViewById<Spinner>(R.id.spn_tnrc_listiem).selectedItem)!!
                    nrcno = childView.findViewById<EditText>(R.id.tie_nrcno_listiem).text.toString()
                    bnrc = firstnrc+"/"+secondnrc+thirdnrc+nrcno
                    fthName = childView.findViewById<EditText>(R.id.edt_beneficiaryfathername_listiem).text.toString()
                    relationship = childView.findViewById<Spinner>(R.id.spn_relationship_listiem).selectedItem.toString()
                    if(childView.findViewById<EditText>(R.id.edt_beneficiaryage_listiem).text.toString()==""){
                        age = 0
                    }else{
                        age = childView.findViewById<EditText>(R.id.edt_beneficiaryage_listiem).text.toString().toInt()
                    }
                    if(childView.findViewById<EditText>(R.id.edt_beneficiarypercent_listiem).text.toString()==""){
                        percent = 0.0
                    }else{
                        percent = childView.findViewById<EditText>(R.id.edt_beneficiarypercent_listiem).text.toString().toDouble()
                    }
                    if(bname!="" && bnrc!="" && fthName!="" && relationship!="" && age!=0 && percent!=0.0){
                        Log.d("third", "name"+bname+"\n"+bnrc)
                        bnameList.add(bname)
                        bnrcList.add(bnrc)
                        fthNameList.add(fthName)
                        mainRelationshipList.add(relationship)
                        ageList.add(age)
                        percentList.add(BeneficiaryPercent(percent))
                        beneficiaryList.add(Beneficiarys(bname, bnrc, fthName, relationship, age, percent))
                    }
                }
                Log.d("FormThird", Gson().toJson(mainRelationshipList))
            }catch (e: Exception){
                Log.d("Third", e.toString())
            }

            /*
            inflate view and get data from other fragment
             */
            var othercontainer = viewpager.findViewById<LinearLayout>(R.id.other_container)
            for(i in 0..othercontainer.childCount-1){
                val childview: View = othercontainer.getChildAt(i)
                otherfacts = childview.findViewById<EditText>(R.id.edt_otherfacts_listitem).text.toString()
                if(childview.findViewById<EditText>(R.id.edt_otherpercents_listitem).text.toString()==""){
                    otherpercents = 0.0
                }else{
                    otherpercents = childview.findViewById<EditText>(R.id.edt_otherpercents_listitem).text.toString().toDouble()
                }

                otherfactsList.add(otherfacts)
                otherpercentsList.add(BeneficiaryPercent(otherpercents))
                otherBeneficiaryList.add(OtherBeneficiarys(otherfacts, otherpercents))
            }
            edtotherfacts = viewpager.findViewById<EditText>(R.id.edt_otherfacts).text.toString()
            if(viewpager.findViewById<EditText>(R.id.edt_otherpercents).text.toString()==""){
                edtotherpercents = 0.0
            }else{
                edtotherpercents = viewpager.findViewById<EditText>(R.id.edt_otherpercents).text.toString().toDouble()
            }

            if(edtotherfacts!="" && edtotherpercents!=0.0){
                otherfactsList.add(edtotherfacts)
                otherpercentsList.add(BeneficiaryPercent(edtotherpercents))
                otherBeneficiaryList.add(OtherBeneficiarys(edtotherfacts, edtotherpercents))
            }
            Log.d("Third",
                Gson().toJson(bnameList)+"\n"+Gson().toJson(bnrcList)+"\n"+Gson().toJson(fthNameList)
                        +Gson().toJson(mainRelationshipList)+"\n"+Gson().toJson(ageList)+"\n"+Gson().toJson(percentList)+
                        Gson().toJson(beneficiaryList)+"\n"+Gson().toJson(otherfactsList)+"\n"+Gson().toJson(otherpercentsList)
            )

//            if((bnameList.size==0 && fthNameList.size==0 && mainRelationshipList.size==0 && ageList.size==0 && percentList.size==0) ||
//                (otherfactsList.size==0 && otherpercentsList.size==0)){
//                Toast.makeText(this, "အကျိုးခံစားခွင့်ရမည့်သူအနည်းဆုံးတစ်ယောက်ရှိရမည်", Toast.LENGTH_SHORT).show()
//            }

            if(beneficiaryList.size>0 || otherBeneficiaryList.size>0){
                Log.d("Third", Gson().toJson(percentList)+"\n"+Gson().toJson(otherpercentsList))
                totalAmt = 0.0
                if(percentList.size>0){
                    for(i in 0..percentList.size-1){
                        totalAmt+=percentList.get(i).percent!!
                    }
                }

                if(otherpercentsList.size>0){
                    for(j in 0..otherpercentsList.size-1){
                        totalAmt+=otherpercentsList.get(j).percent!!
                    }
                }

                if(totalAmt>100){
                    Toast.makeText(this, "ရာခိုင်နှုန်းပေါင်းခြင်းသည် ၁၀၀ အောက်နည်းရမည်"+totalAmt, Toast.LENGTH_SHORT).show()
                }else{
                val previewIntent = Intent(this, PreviewStateActivity::class.java)
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
                    putStringArrayListExtra("relationshipList", mainRelationshipList)
                    putIntegerArrayListExtra("ageList", ageList)
                    putExtra("percentList", percentList)
                    putExtra("beneficiaryList", beneficiaryList)
                    putExtra("other_beneficiary_description", otherfactsList)
                    putExtra("other_beneficiary_percent", otherpercentsList)
                    putExtra("otherbeneficiaryList", otherBeneficiaryList)
                    putExtra("userId", userId)
                    putExtra("previous_policy_id", previous_policy_id)
                    putExtra("age_next_year", age_next_year)
                    putExtra("amount", amount)
                    putExtra("phone_no", phone_no)
                    putExtra("soldier",military)
                }
                Log.d("ThirdInfo",
                    Gson().toJson(bnameList)+"\n"+Gson().toJson(bnrcList)+"\n"+Gson().toJson(fthNameList)+"\n"+Gson().toJson(
                        mainRelationshipList
                    )+"\n"+Gson().toJson(ageList)+"\n"+Gson().toJson(percentList)+"\n"+
                            Gson().toJson(otherfactsList)+"\n"+Gson().toJson(otherpercentsList)+"\n"+phno+"\n"+previous_policy_id)
                startActivity(previewIntent)
                }
                bnameList.clear()
                bnrcList.clear()
                fthNameList.clear()
                mainRelationshipList.clear()
                ageList.clear()
                percentList.clear()
                beneficiaryList.clear()
                otherfactsList.clear()
                otherpercentsList.clear()
                otherBeneficiaryList.clear()
            }
            else{
                Toast.makeText(this, "အကျိုးခံစားခွင့်ရမည့်သူအနည်းဆုံးတစ်ယောက်ရှိရမည်", Toast.LENGTH_SHORT).show()
            }






//
//            var edt_bname = viewpager.findViewById<EditText>(R.id.edt_beneficiaryname)
//            var spnbfnrc = viewpager.findViewById<Spinner>(R.id.spn_fnrc)
//            var spnbsnrc = viewpager.findViewById<Spinner>(R.id.spn_snrc)
//            var spnbtnrc = viewpager.findViewById<Spinner>(R.id.spn_tnrc)
//            var edt_bnrcno = viewpager.findViewById<EditText>(R.id.tie_nrcno)
//            var edt_bfathername = viewpager.findViewById<EditText>(R.id.edt_beneficiaryfathername)
//            var spnbrelationship = viewpager.findViewById<Spinner>(R.id.spn_relationship)
//            var edt_bage = viewpager.findViewById<EditText>(R.id.edt_beneficiaryage)
//            var edt_bpercent = viewpager.findViewById<EditText>(R.id.edt_beneficiarypercent)
//
//            if(edt_bname.text.toString()!="" && edt_bnrcno.text.toString()!="" && edt_bfathername.text.toString()!=""
//                && edt_bage.text.toString()!="" && edt_bpercent.text.toString()!=""){
//                edtbname = edt_beneficiaryname.text.toString()
//                var firstnrc = spnbfnrc.selectedItemPosition
//                //var secondnrc =
//                //edtbnrc = firstnrc+"/"+secondnrc+thirdnrc+tie_nrcno.text.toString()
//               // edtbfthName = edt_beneficiaryfathername.text.toString()
//                edtrelationship = spn_relationship.selectedItem.toString()
//                edtage = edt_beneficiaryage.text.toString().toInt()
//                edtpercent = edt_beneficiarypercent.text.toString().toDouble()
//                bnameList.add(edtbname)
//                bnrcList.add(edtbnrc)
//                fthNameList.add(edtbfthName)
//                relationshipList.add(edtrelationship)
//                ageList.add(edtage)
//                percentList.add(BeneficiaryPercent(edtpercent))
//                beneficiaryList.add(Beneficiarys(edtbname, edtbnrc, edtbfthName,+ edtrelationship, edtage, edtpercent))
//            }
//            Toast.makeText(this, name+"\n"+otherfacts, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun tran_mte_firnrc(myan: String):String{
        return mainnrcHashMap.get(myan)!!
    }
    fun tran_me_snrc(myan: String): String{
        return snrcHashMap.get(myan)!!
    }
    fun tran_me_tnrc(myan: String): String{
        return lastnrcHashMap.get(myan)!!
    }
}
