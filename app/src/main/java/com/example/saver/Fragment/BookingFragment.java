package com.example.saver.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.saver.Adapter.AppointmentAdapter;
import com.example.saver.Adapter.BookingAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.AppointmentResponse;
import com.example.saver.Response.BookingResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

import static com.example.saver.Others.PaginationListener.PAGE_START;


public class BookingFragment extends Fragment {

    private RecyclerView rvBooking;
    private LinearLayoutManager layoutManager;
    private BookingAdapter bookingAdapter;
    private Loader loader;
    private Snack snack;
    private String doctorid;

    public BookingFragment() {
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
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);
        
        rvBooking = view.findViewById(R.id.rv_booking);
        
        layoutManager = new LinearLayoutManager(getActivity());
        rvBooking.setHasFixedSize(true);
        rvBooking.setNestedScrollingEnabled(false);
        rvBooking.setLayoutManager(layoutManager);

        if (!ValidUtils.isNetworkAvailable(requireActivity())){
            snack.network("No Internet Connection!..");
        }else {            
            BookingList();
        }
    }

    private void BookingList() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<BookingResponse> call = api.bookings(doctorid);

        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookingResponse> call, @NotNull Response<BookingResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        BookingResponse data = response.body();
                        if(data != null)
                        {
                            boolean status = data.getStatus();
                            String message = data.getMessage();
                            if(status)
                            {
                                loader.dismiss("");
                                List<BookingResponse.Datum> results = data.getData();

                                bookingAdapter = new BookingAdapter(getActivity(), results);
                                rvBooking.setAdapter(bookingAdapter);
                            }
                            else
                            {
                                loader.dismiss("");
                                snack.failure(message);
                            }
                        }
                    }
                    else
                    {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("BookingError", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("BookingException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> BookingList(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("BookingFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}