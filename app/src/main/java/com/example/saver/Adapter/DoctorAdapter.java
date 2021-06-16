package com.example.saver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.saver.R;
import com.example.saver.Response.DoctorResponse;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{

    private Context context;
    private List<DoctorResponse.Data> typeList;
    private boolean isLoaderVisible = false;

    public DoctorAdapter(Context context, List<DoctorResponse.Data> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doctor, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {

        DoctorResponse.Data item = typeList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        if (!TextUtils.isEmpty(item.getDate())){
            viewHolder.cardView.setTitle(item.getDate());
        }else{
            viewHolder.cardView.setTitle("NA");
        }

        //holder.cardView.setTitle(item.getDate());

        holder.docStartTime.setText(item.getStartTime());
        holder.docEndTime.setText(item.getEndTime());
        holder.docTime.setText(item.getTimeZone());

        holder.docStatus.setText(item.getStatus());
        holder.docBooked.setText(item.getBookedby());

       /* if(item.getStatus().equals("0"))
        {
            holder.docStatus.setText("Not Booked");
            holder.docBooked.setText("-");
        }
        else
        {
            holder.docStatus.setText("-");
            holder.docBooked.setText("Booked");
        }*/
    }

    @Override
    public int getItemCount() {
        return typeList == null ? 0 : typeList.size();
    }

    public void addItems(List<DoctorResponse.Data> postItems) {
        typeList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        typeList.add(new DoctorResponse.Data());
        notifyItemInserted(typeList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = typeList.size() - 1;
        DoctorResponse.Data item = getItem(position);
        if (item != null) {
            typeList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        typeList.clear();
        notifyDataSetChanged();
    }

    private DoctorResponse.Data getItem(int position) {
        return typeList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ExpandableCardView cardView;
        private AppCompatTextView docStartTime, docEndTime, docTime, docStatus, docBooked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.ecv_doctor);
            docStartTime = itemView.findViewById(R.id.doc_starttime);
            docEndTime = itemView.findViewById(R.id.doc_endtime);
            docTime = itemView.findViewById(R.id.doc_time);
            docStatus = itemView.findViewById(R.id.doc_status);
            docBooked = itemView.findViewById(R.id.doc_booked);
        }
    }
}
