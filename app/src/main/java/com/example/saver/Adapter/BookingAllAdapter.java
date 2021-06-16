package com.example.saver.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.BookingAllResponse;
import com.example.saver.Response.BookingResponse;

import java.util.List;

import thebat.lib.validutil.ValidUtils;

public class BookingAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private Context context;
    private List<BookingAllResponse.Datum> bookingallresponseList;

    public BookingAllAdapter(Context context, List<BookingAllResponse.Datum> bookingallresponseList) {
        this.context = context;
        this.bookingallresponseList = bookingallresponseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                View viewHolder = inflater.inflate(R.layout.bookingall_adapter, parent, false);
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

        BookingAllResponse.Datum item = bookingallresponseList.get(position);

        switch (getItemViewType(position)) {
            case VIEW_TYPE_NORMAL:
                ViewHolder viewHolder = (ViewHolder) holder;

                if (!TextUtils.isEmpty(item.getDate())){
                    viewHolder.bookDate.setText(item.getDate());
                }else{
                    viewHolder.bookDate.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getBookingno())){
                    viewHolder.bookName.setText(item.getBookingno());
                }else{
                    viewHolder.bookName.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getCusemail())){
                    viewHolder.bookEmail.setText(item.getCusemail());
                }else{
                    viewHolder.bookEmail.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getMobile())){
                    viewHolder.bookMobile.setText(item.getMobile());
                }else{
                    viewHolder.bookMobile.setText("NA");
                }
                if (!TextUtils.isEmpty(item.getReason())){
                    viewHolder.bookReason.setText(item.getReason());
                }else{
                    viewHolder.bookReason.setText("NA");
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
            return position == bookingallresponseList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return bookingallresponseList == null ? 0 : bookingallresponseList.size();
    }

    public void addItems(List<BookingAllResponse.Datum> postItems) {
        bookingallresponseList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        bookingallresponseList.add(new BookingAllResponse.Datum());
        notifyItemInserted(bookingallresponseList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = bookingallresponseList.size() - 1;
        BookingAllResponse.Datum item = getItem(position);
        if (item != null) {
            bookingallresponseList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        bookingallresponseList.clear();
        notifyDataSetChanged();
    }

    private BookingAllResponse.Datum getItem(int position) {
        return bookingallresponseList.get(position);
    }

    private class ProgressHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        public ProgressHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_progress);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView bookDate, bookName, bookEmail, bookMobile, bookReason;
        private Loader loader;
        private Snack snackToast;
        public ViewHolder(View itemView) {
            super(itemView);
            bookDate = itemView.findViewById(R.id.book_date);
            bookName = itemView.findViewById(R.id.book_no);
            bookEmail = itemView.findViewById(R.id.book_email);
            bookMobile = itemView.findViewById(R.id.book_mobile);
            bookReason = itemView.findViewById(R.id.book_reason);
            loader = new Loader(context);
            snackToast = new Snack(context);
        }
    }
}
