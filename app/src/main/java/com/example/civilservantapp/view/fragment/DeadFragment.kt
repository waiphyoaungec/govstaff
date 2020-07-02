package com.example.civilservantapp.view.fragment


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.activity.dead.CheckDead
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.fragment_dead.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DeadFragment : Fragment() {
    lateinit var policy_id : String
    lateinit var date : String
    lateinit var showDate : String
    lateinit var lost : String

    val fetchDataViewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dead, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = SharedPreference(context!!)
        policy_id = sharedPreference.getUserInfo("policy_id")!!

        check_refund.visibility = View.GONE
        check_refund.setOnClickListener {
            if(dead_policy_id.text.isNullOrEmpty()){
                Toast.makeText(context,"please Enter Policy Id",Toast.LENGTH_SHORT).show()

            }
            else {
                val intent = Intent(context!!, CheckDead::class.java)
                // intent.putExtra("policy", "Life/S(B7)0000001")
                intent.putExtra("policy", dead_policy_id.text.toString())
                intent.putExtra("date", date)
                intent.putExtra("showdate", showDate)

                startActivity(intent)
                activity!!.finish()
            }
        }

        dead_policy_id.setText(policy_id)


        choose_date.setOnClickListener {

            val c = Calendar.getInstance();
            val mYear = c.get(Calendar.YEAR);
            val mMonth = c.get(Calendar.MONTH);
            val myDay = c.get(Calendar.DAY_OF_MONTH);
            val datePickerDialog = DatePickerDialog(
                context!!,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    check_refund.visibility = View.VISIBLE
                    date = "$year-$monthOfYear-$dayOfMonth"
                    showDate = "$dayOfMonth/$monthOfYear/$year"
                    dateEdt.setText("$dayOfMonth/$monthOfYear/$year")
                }, mYear, mMonth, myDay
            )
            datePickerDialog.show()
        }
    }


}
