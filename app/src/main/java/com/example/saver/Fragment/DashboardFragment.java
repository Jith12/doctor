package com.example.saver.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.saver.Adapter.BookingAdapter;
import com.example.saver.Adapter.BookingdateAdapter;
import com.example.saver.Adapter.CustomerAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.BookingdateResponse;
import com.example.saver.Response.CountResponse;
import com.example.saver.Response.CustomerResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

public class DashboardFragment extends Fragment {

    RecyclerView rvCustomer, rvBooking;
    LinearLayoutManager layoutManager, layoutManager1;
    CustomerAdapter customerAdapter;
    BookingdateAdapter bookingdateAdapter;
    Loader loader;
    Snack snack;
    String doctorid;
    AppCompatTextView bookingTodaycount, bookingCount, customerCount, selectDate;
    private int mYear, mMonth, mDay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private AppCompatTextView bookingViewall, customerViewall;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);

        rvBooking = view.findViewById(R.id.rv_booking);
        rvCustomer = view.findViewById(R.id.rv_customer);
        selectDate = view.findViewById(R.id.select_date);
        bookingTodaycount = view.findViewById(R.id.booking_todaycount);
        bookingCount = view.findViewById(R.id.booking_count);
        customerCount = view.findViewById(R.id.customer_count);
        bookingViewall = view.findViewById(R.id.booking_viewall);
        customerViewall = view.findViewById(R.id.customer_viewall);

        layoutManager = new LinearLayoutManager(getActivity());
        rvCustomer.setHasFixedSize(true);
        rvCustomer.setNestedScrollingEnabled(false);
        rvCustomer.setLayoutManager(layoutManager);

        layoutManager1 = new LinearLayoutManager(getActivity());
        rvBooking.setHasFixedSize(true);
        rvBooking.setNestedScrollingEnabled(false);
        rvBooking.setLayoutManager(layoutManager1);

        if (!ValidUtils.isNetworkAvailable(requireActivity())){
            snack.network("No Internet Connection!..");
        }
        else
        {
            CountView();
        }


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(calendar.getTime());
        selectDate.setText(date);

        selectDate.setOnClickListener(v -> {
            final Calendar c1 = Calendar.getInstance();
            mYear = c1.get(Calendar.YEAR);
            mMonth = c1.get(Calendar.MONTH);
            mDay = c1.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            //selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            selectDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            date = selectDate.getText().toString();

                            bookingList();
                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        });

        bookingViewall.setOnClickListener(v -> {
            BookingFragmentAll bookingFragmentAll = new BookingFragmentAll();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, bookingFragmentAll);
            fragmentTransaction.commit();
        });

        customerViewall.setOnClickListener(v -> {
            CustomerFragmentAll customerFragmentAll = new CustomerFragmentAll();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, customerFragmentAll);
            fragmentTransaction.commit();
        });

    }

    private void bookingList() {
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<BookingdateResponse> call = api.bookingsdates(doctorid,date);

        call.enqueue(new Callback<BookingdateResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookingdateResponse> call, @NotNull Response<BookingdateResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        BookingdateResponse data = response.body();

                        if(data != null)
                        {
                            boolean Status = data.getStatus();
                            String message = data.getMessage();

                            if(Status)
                            {
                                //loader.dismiss("");
                                List<BookingdateResponse.Datum> results = data.getData();
                                bookingdateAdapter = new BookingdateAdapter(getActivity(), results);
                                rvBooking.setAdapter(bookingdateAdapter);

                                customerList();
                            }
                            else
                            {
                                //loader.dismiss("");
                                snack.failure(message);
                            }
                        }
                        else
                        {
                            //loader.dismiss("");
                            Log.e("Customer Response", response.message());
                        }
                    }
                    else
                    {
                        //loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("Customer Error", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    //loader.dismiss("");
                    Log.e("Customer Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingdateResponse> call, @NotNull Throwable t) {
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> customerList(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("Customer Failure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void customerList() {
        //loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<CustomerResponse> call = api.customers(doctorid);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustomerResponse> call, @NotNull Response<CustomerResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        CustomerResponse data = response.body();

                        if(data != null)
                        {
                            boolean Status = data.getStatus();
                            String message = data.getMessage();

                            if(Status)
                            {
                                //loader.dismiss("");
                                List<CustomerResponse.Datum> results = data.getData();
                                customerAdapter = new CustomerAdapter(getActivity(), results);
                                rvCustomer.setAdapter(customerAdapter);
                            }
                            else
                            {
                                //loader.dismiss("");
                                snack.failure(message);
                            }
                        }
                        else
                        {
                            //loader.dismiss("");
                            Log.e("Customer Response", response.message());
                        }
                    }
                    else
                    {
                        //loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("Customer Error", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    //loader.dismiss("");
                    Log.e("Customer Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CustomerResponse> call, @NotNull Throwable t) {
                //loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> customerList(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("Customer Failure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void CountView() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<CountResponse> call = api.count(doctorid);

        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(@NotNull Call<CountResponse> call, @NotNull Response<CountResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        CountResponse data = response.body();

                        if(data != null)
                        {
                            boolean Status = data.getStatus();
                            String message = data.getMessage();

                            if(Status)
                            {
                                loader.dismiss("");

                                bookingTodaycount.setText(data.getBookingToday());
                                bookingCount.setText(data.getBooking());
                                customerCount.setText(data.getCustomer());

                                bookingList();
                            }
                            else
                            {
                                loader.dismiss("");
                                snack.failure(message);
                            }
                        }
                        else
                        {
                            loader.dismiss("");
                            Log.e("Dashboard Response", response.message());
                        }
                    }
                    else
                    {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("Dashboard Error", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("Dashboard Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CountResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> CountView(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("Dashboard Failure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}