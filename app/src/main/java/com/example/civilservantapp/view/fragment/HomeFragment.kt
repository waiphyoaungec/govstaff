package com.example.civilservantapp.view.fragment


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.activity.*
import com.example.civilservantapp.view.activity.dead.PreCheckDead
import com.example.civilservantapp.view.activity.loan.LoanActivity
import com.example.civilservantapp.view.activity.refund.RefundActivity
import com.example.civilservantapp.view.activity.rest.CheckFullYear
import com.example.civilservantapp.view.activity.rest.CheckRest
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    lateinit var sharedPreferences: SharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = SharedPreference(activity!!)


        rl_lifeinsuranceform.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    val intent = Intent(activity, FormEntryActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(activity, FormEntryActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }

        }
        rl_dead.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, PreCheckDead::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_monthlypay.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, MonthlyPayActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_cashback.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, RefundActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_rentcash.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, CheckPolicy::class.java)
                    intent.putExtra("title","loan")
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_policy_takeout.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, GetContractActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }

        }

        rl_updatejobhistory.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, UpdeJobActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }

        }
        rl_rest.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, CheckPolicy::class.java)
                    intent.putExtra("title","close")
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_full_year_takeout.setOnClickListener {
            if (check_networkconnection()) {
                if (sharedPreferences.getUserInfo("policy_id") == null || sharedPreferences.getUserInfo(
                        "policy_id"
                    ) == ""
                ) {
                    Toast.makeText(activity, "ဒေတာများကိုအရင်ဖြည့်သွင်းပါ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(activity, CheckPolicy::class.java)
                    intent.putExtra("title","fullyear")
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
        rl_exising.setOnClickListener {
            if (check_networkconnection()) {

                    val intent = Intent(activity, ExistingPolicyId::class.java)
                    startActivity(intent)

            } else {
                Toast.makeText(activity, "ကွန်ရက်ချိတ်ဆက်ထားခြင်းမရှိပါ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun check_networkconnection(): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }


}
