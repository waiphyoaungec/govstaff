package com.example.civilservantapp.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.civilservantapp.R
import com.example.civilservantapp.model.Disease
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class DiseaseListAdapter(val diseaseList: ArrayList<Disease>):RecyclerView.Adapter<DiseaseListAdapter.ViewHolder>() {
    var mSelectedItemsIds : SparseBooleanArray = SparseBooleanArray()
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diseaseitemlayout, parent, false)
        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bindItems(diseaseList[position])
        if(!MDetect.isUnicode()){
            holder.chkdisease.text = Rabbit.uni2zg(diseaseList[position].description)
        }else{
            holder.chkdisease.text = diseaseList[position].description
        }
        holder.chkdisease.isChecked = mSelectedItemsIds.get(position)
        if(position==15 && mSelectedItemsIds.get(position)){
                holder.edtotherdisease.visibility=View.VISIBLE
        }else{
            holder.edtotherdisease.visibility=View.GONE
        }
        holder.chkdisease.setOnClickListener {

            checkCheckBox(position, !mSelectedItemsIds.get(position), holder.edtotherdisease)
            Log.d("DiseaseList", position.toString()+"\n"+diseaseList[position].description)


        }
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return diseaseList.size
    }
    //this class is holding the list view
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val chkdisease = itemView.findViewById(R.id.chkdisease) as CheckBox
        val edtotherdisease = itemView.findViewById(R.id.edt_otherdisease) as EditText
        fun bindItems(disease: Disease){

        }
    }
    /**
     * Check the Checkbox if not checked
     */
    fun checkCheckBox(position: Int, value: Boolean, editText: EditText){
        if(value){
            mSelectedItemsIds.put(position, true)
        }else{
            mSelectedItemsIds.delete(position)
        }
        notifyDataSetChanged()
    }
    /*
    Return the selected Checkbox Ids
     */
    fun getSelectedIds(): SparseBooleanArray{
        return mSelectedItemsIds
    }
}
