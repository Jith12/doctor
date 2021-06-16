package com.example.saver.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saver.Adapter.AppointmentAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.AppointmentResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment {

    private RecyclerView rvAppointments;
    private LinearLayoutManager layoutManager;
    private AppCompatTextView today_btn,tomorrow_btn,date_btn,patient_list_title;
    private Calendar myCalendar;
    private AppointmentAdapter appointmentAdapter;
    private Loader loader;
    private Snack snack;
    private String doctorid;

    public AppointmentFragment() {
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
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);

        myCalendar = Calendar.getInstance();
        patient_list_title = view.findViewById(R.id.patient_list_title);
        today_btn = view.findViewById(R.id.today_btn);
        tomorrow_btn = view.findViewById(R.id.tomorrow_btn);
        date_btn = view.findViewById(R.id.date_btn);
        rvAppointments = view.findViewById(R.id.patient_list);

        layoutManager = new LinearLayoutManager(getContext());
        rvAppointments.setHasFixedSize(true);
        rvAppointments.setNestedScrollingEnabled(false);
        rvAppointments.setLayoutManager(layoutManager);

        ListView();

        /*today_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_solid_primary_bg));
        today_btn.setTextColor(getResources().getColor(R.color.white));
        today_btn.setOnClickListener(getActivity());
        tomorrow_btn.setOnClickListener(getActivity());
        date_btn.setOnClickListener(getActivity());*/

    }

    private void ListView() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<AppointmentResponse> call = api.appointmentlist(doctorid);

        call.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentResponse> call, @NotNull Response<AppointmentResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        AppointmentResponse data = response.body();
                        if(data != null)
                        {
                            boolean status = data.getStatus();
                            String message = data.getMessage();
                            if(status)
                            {
                                loader.dismiss("");
                                List<AppointmentResponse.Datum> results = data.getData();

                                appointmentAdapter = new AppointmentAdapter(getActivity(), results);
                                rvAppointments.setAdapter(appointmentAdapter);
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
                        Log.e("AppointmentError", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("AppointmentException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> ListView(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("AppointmentFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}