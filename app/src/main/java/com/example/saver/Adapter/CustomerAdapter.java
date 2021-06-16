package com.example.saver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saver.R;
import com.example.saver.Response.CustomerResponse;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private Context context;
    private List<CustomerResponse.Datum> customerList;

    public CustomerAdapter(Context context, List<CustomerResponse.Datum> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CustomerResponse.Datum customerResponse = customerList.get(position);

        holder.cusName.setText(customerResponse.getName());
        holder.cusEmail.setText(customerResponse.getEmail());
        holder.cusMobile.setText(customerResponse.getMobile());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView cusName, cusEmail, cusMobile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cusName = itemView.findViewById(R.id.cus_name);
            cusEmail = itemView.findViewById(R.id.cus_emailid);
            cusMobile = itemView.findViewById(R.id.cus_mobile);
        }
    }
}
