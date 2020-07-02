package com.example.civilservantapp.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.downloadapk.util.DownloadController
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.LoginActivity
import com.example.civilservantapp.view.ProfileActivity
import com.example.civilservantapp.view.fragment.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_parent_home.*
import me.myatminsoe.mdetect.MDetect


class ParentHomeActivity : AppCompatActivity() {
    var navigationPosition: Int = 0
    val versions : String = "2"
    lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)
        sharedPreference = SharedPreference(this)
        MDetect.init(this)
        initView()
    }


    private fun initView() {
        setSupportActionBar(toolbar)
        setUpDrawerLayout()
        var headerlayout = navigationView.getHeaderView(0)
        var nametxt = headerlayout.findViewById<TextView>(R.id.nav_profilename)
        var phnotxt = headerlayout.findViewById<TextView>(R.id.nav_profilephone)
        nametxt.text = sharedPreference.getUserInfo("name")
        phnotxt.text = sharedPreference.getUserInfo("phone")



        FirebaseMessaging.getInstance().subscribeToTopic("global")
            .addOnCompleteListener { task ->

                if (!task.isSuccessful) {

                }
            }
        if (sharedPreference.getUserInfo("user_id") != null || sharedPreference.getUserInfo("user_id") != "") {
            FirebaseMessaging.getInstance()
                .subscribeToTopic("${sharedPreference.getUserInfo("user_id")}")
                .addOnCompleteListener { task ->

                }
        }


//        if(MDetect.isUnicode()){
//
//            navigationView.menu.getItem(0).setTitle(Rabbit.zg2uni(getString(R.string.lifeTitle)))
//            navigationView.menu.getItem(1).setTitle(Rabbit.zg2uni(getString(R.string.drawer_getcontract)))
//            navigationView.menu.getItem(2).setTitle(Rabbit.zg2uni(getString(R.string.drawer_monthlypay)))
//            navigationView.menu.getItem(3).setTitle(Rabbit.zg2uni(getString(R.string.logout)))
//        }
        //Load Inbox fragment first
        navigationPosition = R.id.navItemHome
        navigateToFragment(HomeFragment())
        navigationView.setCheckedItem(navigationPosition)
        toolbar.title = getString(R.string.title_activity_home)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.home_icon))
        toolbar.setTitleTextColor(Color.WHITE)

        checkUpdate()



        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navItemHome -> {
                    toolbar.title = "ပင်မစာမျက်နှာ"
                    navigationPosition = R.id.navItemHome

                    navigateToFragment(HomeFragment())
                }
                R.id.navItemLifeInsuranceForm -> {
                    if (check_networkconnection()) {
                        if (sharedPreference.getUserInfo("policy_id") == null || sharedPreference.getUserInfo(
                                "policy_id"
                            ) == ""
                        ) {

                            toolbar.title = "အသက်အာမခံ အဆိုလွှာ"
                            navigationPosition = R.id.navItemLifeInsuranceForm
                            navigateToFragment(FlifeInsurance_Fragment())
                        } else {
                            // Toast.makeText(this, "ဒေတာဖြည့်သွင်းပြီးဖြစ်ပါသည်", Toast.LENGTH_SHORT).show()
                            toolbar.title = "အသက်အာမခံ အဆိုလွှာ"
                            navigationPosition = R.id.navItemLifeInsuranceForm
                            navigateToFragment(FlifeInsurance_Fragment())

                        }
                    } else {
                        Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                R.id.navItemgetContract -> {
                    if (check_networkconnection()) {
                        if (sharedPreference.getUserInfo("policy_id") == null || sharedPreference.getUserInfo(
                                "policy_id"
                            ) == ""
                        ) {
                            toolbar.title = "အသက်အာမခံစာချုပ်ရယူရန်"
                            navigationPosition = R.id.navItemgetContract
                            navigateToFragment(GetContractFragment())
                        } else {
                            Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }



                R.id.nav_refund -> {
                    if (!checkPolicy()) {
                        if (check_networkconnection()) {
                            toolbar.title = "အမ်းငွေ"
                            navigationPosition = R.id.nav_loan
                            navigateToFragment(RefundFragment());
                        } else {
                            Toast.makeText(
                                this,
                                "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_loan -> {
                    if (!checkPolicy()) {
                        if (check_networkconnection()) {
                            val intent = Intent(applicationContext, CheckPolicy::class.java)
                            intent.putExtra("title","loan")
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.close -> {
                    if (!checkPolicy()) {
                        if (check_networkconnection()) {
                            val intent = Intent(applicationContext, CheckPolicy::class.java)
                            intent.putExtra("title","close")
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.dead -> {
                    if (!checkPolicy()) {
                        if (check_networkconnection()) {
                            toolbar.title = "သေဆုံးတောင်းခံငွေလျှောက်ထားရန်"
                            navigationPosition = R.id.dead
                            navigateToFragment(DeadFragment())
                        } else {
                            Toast.makeText(
                                this,
                                "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT).show()
                    }

                }
                R.id.full_year -> {
                    if (!checkPolicy()) {
                        if (check_networkconnection()) {
                            val intent = Intent(applicationContext, CheckPolicy::class.java)
                            intent.putExtra("title","fullyear")
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "အချက်အလက်များဖြည့်သွင်းပါ", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.navItemmonthlyPay -> {
                    if (check_networkconnection()) {
                        toolbar.title = "နိုင်ငံ့ဝန်ထမ်း အသက်အာမခံ လစဉ်ကြေး ပေးသွင်းရန်"
                        navigationPosition = R.id.navItemmonthlyPay
                        navigateToFragment(MonthlyPayFragment())
                    } else {
                        Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                R.id.navItemJobUpdate -> {
                    if (check_networkconnection()) {
                        if (sharedPreference.getUserInfo("policy_id") == null || sharedPreference.getUserInfo(
                                "policy_id"
                            ) == ""
                        ) {
                            Toast.makeText(this, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val intent = Intent(this, UpdeJobActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                R.id.nav_existing -> {
                    if (check_networkconnection()) {

                            val intent = Intent(this, ExistingPolicyId::class.java)
                            startActivity(intent)

                    } else {
                        Toast.makeText(this, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                R.id.navItemLogout -> {
                    sharedPreference.saveUserInfo("user_id", "")
                    sharedPreference.saveUserInfo("policyIdKey", "")
                    sharedPreference.saveUserInfo("name", "")
                    sharedPreference.saveUserInfo("username", "")
                    sharedPreference.saveUserInfo("email", "")
                    sharedPreference.saveUserInfo("phone", "")
                    sharedPreference.saveUserInfo("token", "")
                    sharedPreference.saveUserInfo("policy_id", "")
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(loginIntent)
                    finish()
                }
            }
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()
            true
        }
        val view = navigationView.getHeaderView(0)
        val img_profile = view.findViewById<CircleImageView>(R.id.btn_profilename)
        img_profile.setOnClickListener {
            Log.d("test", "Profile Activity")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }


    private fun setUpDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        //update the main content by replacing fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        if (navigationPosition == R.id.navItemHome) {
            AlertDialog.Builder(this).setMessage("Sure to Exit?")
                .setPositiveButton("Yes") { arg0, arg1 ->
                    moveTaskToBack(true)
                    finish()
                }
                .setNegativeButton("No", { arg0, arg1 -> })
                .show()
        } else {
            //Navigate to Inbox Fragment
            navigationPosition = R.id.navItemHome
            navigateToFragment(HomeFragment())
            navigationView.setCheckedItem(navigationPosition)
            toolbar.title = "မူလစာမျက်နှာ"
        }
    }

    @Suppress("DEPRECATION")
    fun check_networkconnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun checkPolicy(): Boolean {
        return sharedPreference.getUserInfo("policy_id") == null || sharedPreference.getUserInfo("policy_id") == ""
    }
    private fun checkUpdate() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("civil_servant")
            .document("X62PlrIibHAxMs4EznDU")
            .addSnapshotListener { documentSnapshot, e ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val version: String = documentSnapshot.get("version").toString() + ""
                    val link: String = documentSnapshot.get("link").toString() + ""
                    if (version != versions) {
                        val alertDialog =
                            AlertDialog.Builder(this@ParentHomeActivity)
                                .create()
                        alertDialog.setTitle("New Updated Version Available")
                        alertDialog.setCancelable(false)
                        alertDialog.setMessage("DownLoad Latest version of the application")
                        alertDialog.setButton(
                            AlertDialog.BUTTON_NEUTRAL,
                            "Cancel"
                        ) { dialogInterface, i ->
                            Toast.makeText(
                                this@ParentHomeActivity,
                                "You need to update new Version",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        alertDialog.setButton(
                            AlertDialog.BUTTON_NEGATIVE,
                            "Download"
                        ) { dialog, which ->
                            dialog.dismiss()
                            val downloadController =
                                DownloadController(this@ParentHomeActivity, link)
                            downloadController.enqueueDownload()
                        }
                        alertDialog.show()
                    } else {
                        Toast.makeText(
                            this@ParentHomeActivity,
                            "Your app is already updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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


}
