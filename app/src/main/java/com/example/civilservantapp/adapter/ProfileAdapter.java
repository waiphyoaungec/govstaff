package com.example.civilservantapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.civilservantapp.R;
import com.example.civilservantapp.model.profile.InsurancePersonInfo;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHlder> {
    private List<InsurancePersonInfo> profileList;
    private Context context;
    private OnClick onClick;
    private int item = 0;
    public ProfileAdapter(Context context,List<InsurancePersonInfo> profileList){
        this.context = context;
        this.profileList = profileList;

    }

    @NonNull
    @Override
    public ProfileHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.profile_item,parent,false);
        return new ProfileHlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHlder holder, int position) {
        if(item == position){
            holder.btn_id.setTextColor(Color.YELLOW);
        }
        else
            holder.btn_id.setTextColor(Color.WHITE);
        holder.btn_id.setText(profileList.get(position).getPolicy_id());

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
    public void selected(int item){
        this.item = item;
        notifyDataSetChanged();
    }
    class ProfileHlder extends RecyclerView.ViewHolder{
        private Button btn_id;
        public ProfileHlder(@NonNull View itemView) {
            super(itemView);
            btn_id = itemView.findViewById(R.id.choose_policy);
            btn_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClick != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            onClick.onclick(postion);
                        }
                    }
                }
            });

        }
    }
   public interface OnClick{
        void onclick(int position);
    }
   public  void setOnBtnClick(OnClick onClick){
        this.onClick = onClick;
    }

}
