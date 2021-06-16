package com.example.saver.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.saver.Adapter.DoctorAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.PaginationListener;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.AddScheduleResponse;
import com.example.saver.Response.DoctorResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

import static com.example.saver.Others.PaginationListener.PAGE_START;

public class ScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView rvDoctor;
    private SwipeRefreshLayout srlClient;
    LinearLayoutManager layoutManager;
    FloatingActionButton fab;
    private DoctorAdapter doctorAdapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage;
    private boolean isLoading = false;
    private Loader loader;
    private Snack snack;
    private String doctorid;

    public ScheduleFragment() {
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
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);

        srlClient = view.findViewById(R.id.srl_client);
        rvDoctor = view.findViewById(R.id.rv_doctor);
        fab = view.findViewById(R.id.fab);

        srlClient.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvDoctor.setHasFixedSize(true);
        rvDoctor.setNestedScrollingEnabled(false);
        rvDoctor.setLayoutManager(layoutManager);

        doctorAdapter = new DoctorAdapter(getActivity(), new ArrayList<>());
        rvDoctor.setAdapter(doctorAdapter);

        rvDoctor.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                DoctorView();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        if (!ValidUtils.isNetworkAvailable(requireActivity())){
            snack.network("No Internet Connection!..");
        }else {
            currentPage = PAGE_START;
            isLastPage = false;
            doctorAdapter.clear();
            srlClient.setRefreshing(false);
            DoctorView();
        }

        fab.setOnClickListener(v -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            AddDialog addDialog = AddDialog.newInstance();
            addDialog.setCancelable(false);
            addDialog.show(ft, "dialog");
        });

    }

    public static class AddDialog extends DialogFragment {

        private AppCompatTextView pickDate, pickStartTime, pickEndTime;
        private int mYear, mMonth, mDay, mHour, mMinute;
        private AppCompatImageView close;
        private NoboButton addSchedule;
        private Loader loader;
        private Snack snack;
        private String doctorid, Newdatevalue;
        private CardView cardDate, cardStartTime, cardEndTime;

        public AddDialog() {
        }

        public static AddDialog newInstance() {
            AddDialog frag = new AddDialog();
            return frag;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogStyle);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_doctor, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            doctorid = Prefs.getString("e_id", null);

            loader = new Loader(getActivity());
            snack = new Snack(getActivity());

            pickDate = view.findViewById(R.id.pick_date);
            pickStartTime = view.findViewById(R.id.pick_starttime);
            pickEndTime = view.findViewById(R.id.pick_endtime);
            cardDate = view.findViewById(R.id.card_date);
            cardStartTime = view.findViewById(R.id.card_starttime);
            cardEndTime = view.findViewById(R.id.card_endtime);
            close = view.findViewById(R.id.close);
            addSchedule = view.findViewById(R.id.add_schedule);

            cardDate.setOnClickListener(v -> {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                pickDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                Newdatevalue = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            });

            cardStartTime.setOnClickListener(v -> {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                pickStartTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            });

            cardEndTime.setOnClickListener(v -> {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                pickEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            });

            close.setOnClickListener(v -> {
                getDialog().dismiss();
            });

            addSchedule.setOnClickListener(v -> {
                String date = Newdatevalue;
                String starttime = pickStartTime.getText().toString();
                String endtime = pickEndTime.getText().toString();
                AddDoctor(date, starttime, endtime);
            });
        }

        private void AddDoctor(String date, String starttime, String endtime) {
            loader.show("");
            RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
            Call<AddScheduleResponse> call = api.addschedule(doctorid, date, starttime, endtime);

            call.enqueue(new Callback<AddScheduleResponse>() {
                @Override
                public void onResponse(@NotNull Call<AddScheduleResponse> call, @NotNull Response<AddScheduleResponse> response) {
                    try {
                        if(response.isSuccessful())
                        {
                            AddScheduleResponse data = response.body();
                            loader.dismiss("");
                            if(data != null)
                            {
                                boolean status = data.getStatus();
                                String message = data.getMessage();

                                if(status)
                                {
                                    snack.success(message);
                                    getDialog().dismiss();
                                    Fragment currentFragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.container);
                                    FragmentTransaction fragmentTransaction = requireFragmentManager().beginTransaction();
                                    fragmentTransaction.detach(Objects.requireNonNull(currentFragment));
                                    fragmentTransaction.attach(currentFragment);
                                    fragmentTransaction.commit();
                                }
                                else
                                {
                                    snack.failure(message);
                                }
                            }
                        }
                        else
                        {
                            loader.dismiss("");
                            ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                            snack.failure(errorUtils.message());
                            Log.e("DoctorAddError", Objects.requireNonNull(errorUtils.message()));
                        }
                    }
                    catch (Exception e)
                    {
                        loader.dismiss("");
                        Log.e("DoctorAddException", Objects.requireNonNull(e.getMessage()));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddScheduleResponse> call, @NotNull Throwable t) {
                    loader.dismiss("");
                    if (t instanceof SocketTimeoutException){
                        snack.timeout("Timeout, Retrying Again. Please Wait");
                        new Handler().postDelayed(() -> AddDoctor(date, starttime, endtime), 3000);
                    }else if (t instanceof UnknownHostException){
                        snack.host("Unknown Host, Check your URL");
                    }
                    Log.e("DoctorAddFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }
    }

    private void DoctorView() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<DoctorResponse> call = api.doctorlist(doctorid, currentPage,10);

        call.enqueue(new Callback<DoctorResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorResponse> call, @NotNull Response<DoctorResponse> response) {

                try {
                    if(response.isSuccessful())
                    {
                        DoctorResponse data = response.body();
                        if(data != null)
                        {
                            boolean status = data.getStatus();
                            String message = data.getMessage();
                            if(status)
                            {
                                loader.dismiss("");
                                List<DoctorResponse.Data> results = data.getData();

                                totalPage = data.getTotal();

                                if (currentPage != PAGE_START) doctorAdapter.removeLoading();
                                doctorAdapter.addItems(results);
                                srlClient.setRefreshing(false);

                                if (currentPage < totalPage) {
                                    doctorAdapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;
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
                        Log.e("DoctorError", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("DoctorException", Objects.requireNonNull(e.getMessage()));
                }

            }

            @Override
            public void onFailure(@NotNull Call<DoctorResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> DoctorView(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("DoctorFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        currentPage = PAGE_START;
        isLastPage = false;
        doctorAdapter.clear();
        srlClient.setRefreshing(false);
        DoctorView();
    }
}