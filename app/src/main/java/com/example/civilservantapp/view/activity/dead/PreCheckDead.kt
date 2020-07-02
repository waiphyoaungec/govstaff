package com.example.civilservantapp.view.activity.dead

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.civilservantapp.R
import kotlinx.android.synthetic.main.activity_dead.*

class PreCheckDead : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_check_dead)

        tool_die.title = "သေဆုံးလျှောက်လွှာတင်ရန်"
        tool_die.navigationIcon = ContextCompat.getDrawable(applicationContext,R.drawable.back_arrow)
        setSupportActionBar(tool_die)
        tool_die.setNavigationOnClickListener {
            finish()
        }
    }
}
