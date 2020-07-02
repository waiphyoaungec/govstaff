package com.example.civilservantapp.view.activity.refund

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import kotlinx.android.synthetic.main.activity_get_contract.*
import kotlinx.android.synthetic.main.activity_loan.*
import kotlinx.android.synthetic.main.activity_refund.*
import kotlinx.android.synthetic.main.activity_refund.tool_refund
import kotlinx.android.synthetic.main.fragment_refund.*
import java.util.*
import kotlin.properties.Delegates


class RefundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund)




        tool_refund.title = "ငွေထုတ်ယူရန်"
        tool_refund.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.back_arrow))
        setSupportActionBar(tool_refund)
        tool_refund.setNavigationOnClickListener {
            finish()
        }


    }
}
