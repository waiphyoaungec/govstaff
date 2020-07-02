package com.example.civilservantapp.view.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.util.InputFilter
import kotlinx.android.synthetic.main.fragment_other.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class OtherFragment : Fragment() {
lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(activity!!)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edt_otherpercents.setFilters(arrayOf<android.text.InputFilter>(InputFilter("1", "100")))
        btnAddMore_other.setOnClickListener {
            if(othercontrols_empty()){

            }else{
                val addview = getLayoutInflater().inflate(R.layout.otherbeneficiary_listitem, null)
                val lbl_otherfacts_listitem = addview.findViewById<TextView>(R.id.lbl_otherfacts_listitem)
                val edt_otherfacts_listitem =addview.findViewById<EditText>(R.id.edt_otherfacts_listitem)
                val lbl_otherpercents_listitem = addview.findViewById<TextView>(R.id.lbl_otherpercents_listitem)
                val edt_otherpercents_listitem = addview.findViewById<EditText>(R.id.edt_otherpercents_listitem)
                val btndelete_listiem = addview.findViewById<ImageView>(R.id.btndelete_listitem)
                if(MDetect.isUnicode()){
                    lbl_otherfacts_listitem.text = Rabbit.zg2uni(getString(R.string.lbl_otherfacts))
                    edt_otherfacts_listitem.hint = Rabbit.zg2uni(getString(R.string.supply_hint))
                    lbl_otherpercents_listitem.text = Rabbit.zg2uni(getString(R.string.lbl_otherpercents))
                    edt_otherpercents_listitem.hint = Rabbit.zg2uni(getString(R.string.supply_hint))
                }
                //transferring data to dynamic layout
                edt_otherfacts_listitem.setText(edt_otherfacts.text.toString())
                edt_otherpercents_listitem.setText(edt_otherpercents.text.toString())
                //adding dynmaic view to container
                other_container.addView(addview)
                btndelete_listiem.setOnClickListener {
                    other_container.removeView(it.parent.parent as View)
                }
                clearTextFields()
            }

        }
    }

    fun clearTextFields(){
        edt_otherfacts.setText("")
        edt_otherpercents.setText("")
        edt_otherfacts.requestFocus()
        hideKeyboard()
    }

    fun hideKeyboard(){
        val view = activity!!.findViewById<View>(android.R.id.content)
        if(view!=null){
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun othercontrols_empty():Boolean{
        if(edt_otherfacts.text.toString()=="" && edt_otherpercents.text.toString()==""){
            return true
        }else{
            return false
        }
    }
}
