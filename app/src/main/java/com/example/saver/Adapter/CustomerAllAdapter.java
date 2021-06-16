package com.example.saver.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.BookingAllResponse;
import com.example.saver.Response.CustomerAllResponse;

import java.util.List;

public class CustomerAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private Context context;
    private List<CustomerAllResponse.Datum> customerList;

    public CustomerAllAdapter(Context context, List<CustomerAllResponse.Datum> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                View viewHolder = inflater.inflate(R.layout.customerall_adapter, parent, false);
                holder = new ViewHolder(viewHolder);
                break;
            case VIEW_TYPE_LOADING:
                View progressHolder = inflater.inflate(R.layout.progress_adapter, parent, false);
                holder = new ProgressHolder(progressHolder);
                break;
            default:
                return null;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CustomerAllResponse.Datum item = customerList.get(position);

        switch (getItemViewType(position)) {
            case VIEW_TYPE_NORMAL:
                ViewHolder viewHolder = (ViewHolder) holder;

                if (!TextUtils.isEmpty(item.getName())){
                    viewHolder.cusName.setText(item.getName());
                }else{
                    viewHolder.cusName.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getEmail())){
                    viewHolder.cusEmail.setText(item.getEmail());
                }else{
                    viewHolder.cusEmail.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getMobile())){
                    viewHolder.cusMobile.setText(item.getMobile());
                }else{
                    viewHolder.cusMobile.setText("NA");
                }

                break;
            case VIEW_TYPE_LOADING:
                ProgressHolder progressHolder = (ProgressHolder) holder;
                progressHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == customerList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return customerList == null ? 0 : customerList.size();
    }

    public void addItems(List<CustomerAllResponse.Datum> postItems) {
        customerList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        customerList.add(new CustomerAllResponse.Datum());
        notifyItemInserted(customerList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = customerList.size() - 1;
        CustomerAllResponse.Datum item = getItem(position);
        if (item != null) {
            customerList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        customerList.clear();
        notifyDataSetChanged();
    }

    private CustomerAllResponse.Datum getItem(int position) {
        return customerList.get(position);
    }

    private class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        public ProgressHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_progress);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView cusName, cusEmail, cusMobile;
        public ViewHolder(View itemView) {
            super(itemView);
            cusName = itemView.findViewById(R.id.cus_name);
            cusEmail = itemView.findViewById(R.id.cus_emailid);
            cusMobile = itemView.findViewById(R.id.cus_mobile);
        }
    }
}
