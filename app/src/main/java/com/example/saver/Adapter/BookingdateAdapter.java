package com.example.saver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saver.R;
import com.example.saver.Response.BookingdateResponse;

import java.util.List;

public class BookingdateAdapter extends RecyclerView.Adapter<BookingdateAdapter.ViewHolder> {

    private Context context;
    private List<BookingdateResponse.Datum> bookingdateresponse;

    public BookingdateAdapter(Context context, List<BookingdateResponse.Datum> bookingdateresponse) {
        this.context = context;
        this.bookingdateresponse = bookingdateresponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bookdate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookingdateResponse.Datum item = bookingdateresponse.get(position);

        holder.bookDate.setText(item.getDate());
        holder.bookName.setText(item.getBookingno());
        holder.bookEmail.setText(item.getCusemail());
        holder.bookMobile.setText(item.getMobile());
        holder.bookReason.setText(item.getReason());
    }

    @Override
    public int getItemCount() {
        return bookingdateresponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView bookDate, bookName, bookEmail, bookMobile, bookReason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookDate = itemView.findViewById(R.id.book_date);
            bookName = itemView.findViewById(R.id.book_no);
            bookEmail = itemView.findViewById(R.id.book_email);
            bookMobile = itemView.findViewById(R.id.book_mobile);
            bookReason = itemView.findViewById(R.id.book_reason);
        }
    }
}
