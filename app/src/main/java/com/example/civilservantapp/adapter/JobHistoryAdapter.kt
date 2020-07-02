package com.example.civilservantapp.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.civilservantapp.R
import com.example.civilservantapp.model.profile.HistoryData
import kotlinx.android.synthetic.main.pf_jobhistory_rows.view.*

class JobHistoryAdapter : PagedListAdapter<HistoryData, JobHistoryAdapter.JobHistoryViewHolder>(USER_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pf_jobhistory_rows, parent, false)
        return JobHistoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: JobHistoryViewHolder, position: Int) {
            val jobHistory: HistoryData? = getItem(position)
        if(position==0){
            jobHistory?.let { holder.bind(it, "စဉ်") }
        }else{
            jobHistory?.let { holder.bind(it, (position).toString()) }
        }


    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
    class JobHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val norow = view.norow
        private val jobrow = view.jobrow
        private val sddtrow = view.sddtrow
        private val mdrow = view.mdrow
        fun bind(hd: HistoryData, position: String) {
            if(position.equals("စဉ်")){
                norow.setTypeface(null, Typeface.BOLD)
                jobrow.setTypeface(null, Typeface.BOLD)
                sddtrow.setTypeface(null, Typeface.BOLD)
                mdrow.setTypeface(null, Typeface.BOLD)
            }
            norow.setText(position)
            jobrow.setText(hd.occupation)
            sddtrow.setText(hd.mm_state_region_name + " - "+hd.mm_district_name+ " - "+hd.mm_township_name)
            mdrow.setText(hd.mm_ministry_name+ " - "+ hd.mm_dept_name)
        }
    }
    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<HistoryData>() {
            override fun areItemsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean =
                newItem == oldItem
        }
    }
}