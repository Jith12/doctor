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

import com.example.saver.Adapter.BookingAdapter;
import com.example.saver.Adapter.ProductAdapter;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.BookingResponse;
import com.example.saver.Response.ProductResponse;
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

public class MessagesFragment extends Fragment {

    private Loader loader;
    private Snack snack;
    private RecyclerView rvProducts;
    private LinearLayoutManager layoutManager;
    private ProductAdapter productAdapter;
    private String doctorid;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = new Loader(getActivity());
        snack = new Snack(getActivity());

        doctorid = Prefs.getString("e_id", null);

        rvProducts = view.findViewById(R.id.rv_product);

        layoutManager = new LinearLayoutManager(getActivity());
        rvProducts.setNestedScrollingEnabled(false);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(layoutManager);

        if (!ValidUtils.isNetworkAvailable(requireActivity())){
            snack.network("No Internet Connection!..");
        }else {
            productList();
        }
    }

    private void productList() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
        Call<ProductResponse> call = api.productlist();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProductResponse> call, @NotNull Response<ProductResponse> response) {
                try {
                    if(response.isSuccessful())
                    {
                        ProductResponse data = response.body();
                        if(data != null)
                        {
                            boolean status = data.getStatus();
                            String message = data.getMessage();
                            if(status)
                            {
                                loader.dismiss("");
                                List<ProductResponse.Datum> results = data.getData();

                                productAdapter = new ProductAdapter(getActivity(), results);
                                rvProducts.setAdapter(productAdapter);
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
            public void onFailure(@NotNull Call<ProductResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> productList(), 3000);
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