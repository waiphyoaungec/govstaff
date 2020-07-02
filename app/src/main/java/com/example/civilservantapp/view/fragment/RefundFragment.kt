package com.example.civilservantapp.view.fragment


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.view.activity.refund.CheckRefundActivity
import kotlinx.android.synthetic.main.fragment_refund.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RefundFragment : Fragment() {
    lateinit var policy_id : String
    lateinit var date : String
    lateinit var showDate : String
    lateinit var rdo_lost : RadioButton
    lateinit var rdo_nolost : RadioButton
    lateinit var ctx : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_refund, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = context!!
        val sharedPreference = SharedPreference(ctx)
        policy_id  = sharedPreference.getUserInfo("policy_id")!!
        check_refund.visibility = View.GONE
        rdo_lost = view.findViewById(R.id.lost)
        rdo_nolost = view.findViewById(R.id.no_lost)



        check_refund.setOnClickListener {
            val intent = Intent(ctx, CheckRefundActivity::class.java)
           //intent.putExtra("policy","Life/S(B7)0000001")
            if(refund_policy_id.text.isNullOrEmpty()){
                Toast.makeText(context, "please enter policy id", Toast.LENGTH_SHORT).show()
            }
            else{
                intent.putExtra("policy",refund_policy_id.text.toString())
                intent.putExtra("date",date)
                intent.putExtra("showdate",showDate)
                intent.putExtra("lost", checkLost())
                startActivity(intent)
                activity!!.finish()
            }


        }

        refund_policy_id.setText(policy_id)
        choose_date.setOnClickListener {

            val c = Calendar.getInstance();
            val mYear = c.get(Calendar.YEAR);
            val mMonth = c.get(Calendar.MONTH);
            val myDay = c.get(Calendar.DAY_OF_MONTH);
            val datePickerDialog = DatePickerDialog(
                ctx,
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
    fun checkLost() : Int{
        if(rdo_lost.isChecked)
            return 1
        else
            return 0
    }


}
