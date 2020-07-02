package com.example.civilservantapp.view

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.adapter.JobHistoryAdapter
import com.example.civilservantapp.adapter.ProfileAdapter
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.model.profile.HistoryData
import com.example.civilservantapp.model.profile.InsurancePersonInfo
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import com.example.civilservantapp.viewmodel.JobHistoryViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.toolbar
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    private var start = 0
    lateinit var progressDialog: CustomProgressBar
    private var user_id: String = ""
    private var insurance_person_id: Int =0
    private var TAG: String=this.javaClass.simpleName
    private val adapter = JobHistoryAdapter()
    private  var isViewMore = true
    private var isJobMore = true
    lateinit var personinfo : List<InsurancePersonInfo>
    lateinit var policy_adapter : ProfileAdapter
    lateinit var  personLayoutParms : ViewGroup.LayoutParams
    lateinit var jobLayoutParms : ViewGroup.LayoutParams
    private val fetchDataViewModel: FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        progressDialog = CustomProgressBar()
        personLayoutParms = person_layout.layoutParams
        jobLayoutParms = job_layout.layoutParams
        //loading_change_office.visibility = View.GONE
        viewMoreLess()
        jobViewMoreLess()
        view_more.setOnClickListener {

            viewMoreLess()
        }
        person_layout.setOnClickListener{
           viewMoreLess()
        }
        view_more.setOnClickListener {
            viewMoreLess()
        }
        job_imageview.setOnClickListener {
            jobViewMoreLess()
        }
        job_layout.setOnClickListener{
            jobViewMoreLess()
        }
        info_viewmore.setOnClickListener{
            jobViewMoreLess()
        }

        start = 0
        MDetect.init(this)
        sharedPreference = SharedPreference(this)
        user_id = sharedPreference.getUserInfo("user_id")!!
        nodata.text = "${sharedPreference.getUserInfo("name")} \n" +
                "${sharedPreference.getUserInfo("phone")} \n\n အာမခံပေါ်လစီစာချုပ်မရှိသေးပါ"

        Log.d(TAG, user_id)
        if(user_id!=""){
            fetchDataViewModel.getUserProfile(user_id.toInt())
        }
        fetchDataViewModel.getUserProfile.observe(this, Observer {
            when(it){

                is NetWorkViewState.Loading->{
                    Log.d(TAG,"is showing")
                    if(start == 0) {
                       progressDialog.show(this,"Loading..")

                    }
                }
                is NetWorkViewState.Success->{
                    Log.d(TAG, "success is success")

                    if(it.item.message.equals("false")){
                       // Toast.makeText(applicationContext,"false",Toast.LENGTH_SHORT).show()
                        Log.d(TAG,"false")
                        loading.visibility = View.GONE
                        nodata.visibility=View.VISIBLE
                        profileimg.visibility=View.VISIBLE
                        card_personinfo.visibility= View.GONE
                        card_insuranceinfo.visibility=View.GONE
                        card_premiuminfo.visibility=View.GONE
                        cardView2.visibility = View.GONE
                    }else if(it.item.message.equals("true")){
                      //  Toast.makeText(applicationContext,"true",Toast.LENGTH_SHORT).show()
                        Log.d(TAG,"true")
                        nodata.visibility=View.GONE
                        profileimg.visibility=View.VISIBLE
                        card_personinfo.visibility= View.VISIBLE
                        card_insuranceinfo.visibility=View.VISIBLE
                        card_premiuminfo.visibility=View.VISIBLE
                        all_info_text.text = "ပေါ်လစီအိုင်ဒီ (${it.item.insurance_person_info!!.size}) ခုလျှောက်ထားသည်"
                        personinfo = it.item.insurance_person_info!!
                        policy_adapter = ProfileAdapter(this,personinfo)
                        info_list.setHasFixedSize(true)
                        info_list.layoutManager = LinearLayoutManager(this@ProfileActivity,LinearLayoutManager.HORIZONTAL,false)
                        info_list.adapter = policy_adapter
                        showPolicyId(personinfo.get(0))
                        policy_adapter.setOnBtnClick {
                            showPolicyId(personinfo.get(it))
                            policy_adapter.selected(it)

                        }


                        if(it.item.payment_info!!.msg!="error"){
                            it.item.payment_info.let {
                                it.apply {
                                    txtpfcountpaidmonth.setText(it!!.count_paid_month.toString() + " လ")
                                    txtpfpaidmonth.setText("")
                                    for(i in 0..it!!.paid_month!!.size-1){
                                        txtpfpaidmonth.append(convert_ymd_dmy(it!!.paid_month!!.get(i))+"\n")
                                    }
                                    txtpfcountduemonth.setText(it!!.count_overdue_m.toString() + " လ")
                                    txtpfduemonth.setText("")
                                    for(i in 0..it!!.overdue_month!!.size-1){
                                        txtpfduemonth.append(convert_ymd_dmy(it!!.overdue_month!!.get(i))+"\n")
                                    }
                                    txtpfduedate.setText(convert_ymd_dmy(it!!.due_date!!))
                                    txtpfmonthlyamt.setText(it!!.monthly_amount.toString() + " ကျပ်")
                                    txtpftotaldepamt.setText(it!!.total_deposit_amt.toString() + " ကျပ်")
                                }
                            }
                        }

                    }


                    progressDialog.dialog.dismiss()
                }
                is NetWorkViewState.Error->{
                    nodata.text = "error"
                    loading.visibility = View.GONE
                    nodata.visibility=View.VISIBLE
                    profileimg.visibility=View.VISIBLE
                    card_personinfo.visibility= View.GONE
                    card_insuranceinfo.visibility=View.GONE
                    card_premiuminfo.visibility=View.GONE
                    cardView2.visibility = View.GONE
                    Toast.makeText(applicationContext,"မအောင်မြင်ပါ",Toast.LENGTH_SHORT).show()
                    Log.d(TAG, it.errormessage.message.toString() + "hello")
                    progressDialog.dialog.dismiss()
                }
            }
        })




        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {

            finish()
        }

    }

    fun showPolicyId(it : InsurancePersonInfo){
        insurance_person_id = it.id
        Log.d(TAG, "insurance_id"+insurance_person_id!!.toString())
        sharedPreference.saveUserInfo("policy_id", it.policy_id)
        txtprofilename.setText(it.name)

        txtprofilepolicyid.setText(it.policy_id)
        txtprofilegender.setText(it.gender)
        txtprofilenrc.setText(it.nrc)
        txtprofilefathername.setText(it.father_name)
        txtprofileoccupation.setText(it.occupation)
        var townshipData = it.mm_state_region_name+" - " + it.mm_district_name + " - " + it.mm_township_name
        txtprofiletownship.setText(townshipData)
        var departmentData = it.mm_ministry_name + " - " + it.mm_dept_name
        txtprofiledepartment.setText(departmentData)
        txtprofileaddress.setText(it.permanent_address)
        txtprofilephno.setText(it.phone_no)
        txtprofiledob.setText(convert_ymd_dmy(it.date_of_birth))
        txtprofilepob.setText(it.birth_place)
        txtprofilemark.setText(it.mark)
        txtprofileweight.setText(it.weight + " ပေါင်")
        txtprofileheight.setText(it.height)
        txtprofilesalary.setText(it.salary.toString() + " ကျပ်")
        //bind premium information data
        txtprofilepremiumkyat.setText(it.premium_kyat.toString() + " ကျပ်")
        if(it.premium_status.equals(null)){
            txtprofilepremiumstatus.setText("-")
        }
        if(it.previous_premium_status.equals(0)){
            txtprofilepreviouspremiumstatus.setText("မရှိ")
        }else{
            txtprofilepreviouspremiumstatus.setText("ရှိ")
        }
        if(it.previous_acceptance_status.equals(0)){
            txtprofilepreviouspremiumaccpt.setText("မရှိ")
        }else{
            txtprofilepreviouspremiumaccpt.setText("ရှိ")
        }

        if(!it.army.isEmpty() && it.army.equals("1"))
            profile_military_text.setText("တပ်မတော်သား")
        else
            profile_military_text.setText("အခြား")
        //bind insurance information data
        txtpfinsuranceamt.setText(it.insurance_amount.toString() + " ကျပ်")
        txtpfinsuranceperiod.setText(it.insurance_peroid.toString() + " နှစ်")
        fetchDataViewModel.getHistory(insurance_person_id!!)
        loading.visibility = View.VISIBLE
        jobhistoryrecycler.layoutManager = LinearLayoutManager(this@ProfileActivity)

        jobhistoryrecycler.adapter = adapter
        fetchDataViewModel.jobHistoryPageList.observe(this@ProfileActivity, Observer {
            jobhistorytitle.visibility=View.VISIBLE
            adapter.submitList(it)
            //loading.visibility = View.GONE
        })
    }

    override fun onPause() {
        super.onPause()
        if(progressDialog.dialog.isShowing){
            start = 1
            progressDialog.dialog.dismiss()


        }

    }



    fun convert_ymd_dmy(ymd: String): String{
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val sdf2 = SimpleDateFormat("dd-MM-yyyy")
        var df2 = sdf2.format(sdf1.parse(ymd))
        return df2
    }
    fun viewMoreLess(){
        if(!isViewMore){
            personLayoutParms.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            person_layout.layoutParams = personLayoutParms
            view_more.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.view_less))
            info_viewmore.text = "View Less"
            isViewMore = true
        }
        else{
            personLayoutParms.height = 300
            person_layout.layoutParams = personLayoutParms
            view_more.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.view_more))
            info_viewmore.text = "View More"
            isViewMore = false
        }
    }
    fun jobViewMoreLess(){
        if(!isJobMore){
            jobLayoutParms.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            job_layout.layoutParams = jobLayoutParms
            job_imageview.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.view_less))
            job_viewmore.text = "View Less"
            isJobMore = true
        }
        else{
            jobLayoutParms.height = 300
            job_layout.layoutParams = jobLayoutParms
            job_imageview.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.view_more))
            job_viewmore.text = "View More"
            isJobMore = false
        }
    }
}
