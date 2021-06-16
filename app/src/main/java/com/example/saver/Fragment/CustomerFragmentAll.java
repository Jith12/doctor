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
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.example.saver.Adapter.BookingAllAdapter;
import com.example.saver.Adapter.CustomerAllAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.PaginationListener;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.BookingAllResponse;
import com.example.saver.Response.CustomerAllResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thebat.lib.validutil.ValidUtils;

import static com.example.saver.Others.PaginationListener.PAGE_START;


public class CustomerFragmentAll extends Fragment {

    private RecyclerView rvCustomer;
    private LinearLayoutManager layoutManager;
    private CustomerAllAdapter customerAllAdapter;
    private List<CustomerAllResponse> customerAllResponseList = new ArrayList<>();
    private Loader loader;
    private Snack snack;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage;
    private boolean isLoading = false;
    private String doctorid;
    private ViewStub viewStub;
    private LinearLayout layout;

    public CustomerFragmentAll() {
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
        return inflater.inflate(R.layout.fragment_customer_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);

        rvCustomer = view.findViewById(R.id.rv_customer);
        viewStub = view.findViewById(R.id.customer_empty);
        layout = view.findViewById(R.id.customer_layout);

        layoutManager = new LinearLayoutManager(getActivity());
        rvCustomer.setHasFixedSize(true);
        rvCustomer.setNestedScrollingEnabled(false);
        rvCustomer.setLayoutManager(layoutManager);

        customerAllAdapter = new CustomerAllAdapter(getActivity(), new ArrayList<>());
        rvCustomer.setAdapter(customerAllAdapter);

        rvCustomer.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                customerAll();
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

        if (ValidUtils.isNetworkAvailable(requireActivity())){
            currentPage = PAGE_START;
            isLastPage = false;
            customerAllAdapter.clear();
            customerAll();
        }
    }

    private void customerAll() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<CustomerAllResponse> call = api.customerlistall(doctorid, currentPage, 10);

        call.enqueue(new Callback<CustomerAllResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustomerAllResponse> call, @NotNull Response<CustomerAllResponse> response) {
                try {
                    if (response.isSuccessful()){
                        CustomerAllResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                requireActivity().runOnUiThread(() -> {
                                    viewStub.setVisibility(View.GONE);
                                    layout.setVisibility(View.VISIBLE);
                                });
                                List<CustomerAllResponse.Datum> result = data.getData();
                                totalPage = data.getTotal();
                                if (currentPage != PAGE_START) customerAllAdapter.removeLoading();
                                customerAllAdapter.addItems(result);
                                if (currentPage < totalPage) {
                                    customerAllAdapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;
                            }else {
                                snack.timeout(message);
                                if (message.equalsIgnoreCase("No Customer Available")){
                                    requireActivity().runOnUiThread(() -> {
                                        viewStub.setVisibility(View.VISIBLE);
                                        layout.setVisibility(View.GONE);
                                    });
                                }
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("CusError", Objects.requireNonNull(errorUtils.message()));
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("CusException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CustomerAllResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> customerAll(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("CusFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}