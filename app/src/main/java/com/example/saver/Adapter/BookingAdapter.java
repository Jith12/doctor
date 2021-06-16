package com.example.saver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saver.R;
import com.example.saver.Response.BookingResponse;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private Context context;
    private List<BookingResponse.Datum> bookingList;

    public BookingAdapter(Context context, List<BookingResponse.Datum> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookingResponse.Datum item = bookingList.get(position);

        holder.bookDate.setText(item.getScheduleId());
        holder.bookName.setText(item.getCustomerName());
        holder.bookEmail.setText(item.getCustomerEmail());
        holder.bookMobile.setText(item.getCustomerMobile());
        holder.bookReason.setText(item.getReason());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView bookDate, bookName, bookEmail, bookMobile, bookReason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookDate = itemView.findViewById(R.id.book_date);
            bookName = itemView.findViewById(R.id.book_customer);
            bookEmail = itemView.findViewById(R.id.book_email);
            bookMobile = itemView.findViewById(R.id.book_mobile);
            bookReason = itemView.findViewById(R.id.book_reason);
        }
    }
}
