package com.example.saver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saver.R;
import com.example.saver.Response.AppointmentResponse;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AppointmentResponse.Datum> datumList;

    public AppointmentAdapter(Context context, List<AppointmentResponse.Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_appointment_title, parent, false);
            return new ViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_appointment, parent, false);
            return new PatientDetailViewHolder(v);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try{
            if(holder instanceof PatientDetailViewHolder){
                AppointmentResponse.Datum item = datumList.get(position-1);
                ((PatientDetailViewHolder) holder).pt_id.setText(item.getId());
                ((PatientDetailViewHolder) holder).pt_date.setText(item.getDate());
                ((PatientDetailViewHolder) holder).pt_start.setText(item.getStartTime());
                ((PatientDetailViewHolder) holder).pt_end.setText(item.getEndTime());
                ((PatientDetailViewHolder) holder).pt_time.setText(item.getTimezone());

                if(item.getStatus().equals("0"))
                {
                    ((PatientDetailViewHolder) holder).pt_status.setText("Not Booked");
                    ((PatientDetailViewHolder) holder).pt_status.setTextColor(Color.parseColor("#a90000"));
                }
                else
                {
                    ((PatientDetailViewHolder) holder).pt_status.setText("Booked");
                    ((PatientDetailViewHolder) holder).pt_status.setTextColor(Color.parseColor("#000000"));
                }

                /*holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, PatientDetailActivity.class);
                    intent.putExtra("userid", "4");
                    context.startActivity(intent);
                    Animatoo.animateFade(context);
                });*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    @Override
    public int getItemCount() {
        return datumList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class PatientDetailViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView pt_id,pt_date, pt_start, pt_end, pt_time, pt_status, pt_booked;
        public PatientDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_id = itemView.findViewById(R.id.patient_id);
            pt_date = itemView.findViewById(R.id.patient_date);
            pt_start = itemView.findViewById(R.id.patient_start);
            pt_end = itemView.findViewById(R.id.patient_end);
            pt_time = itemView.findViewById(R.id.patient_time);
            pt_status = itemView.findViewById(R.id.patient_status);
            //pt_booked = itemView.findViewById(R.id.patient_booked);
        }
    }
}
