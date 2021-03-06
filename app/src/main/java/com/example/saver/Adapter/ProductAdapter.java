package com.example.saver.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.ProductResponse;
import com.example.saver.Response.ShareResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductResponse.Datum> productList;

    public ProductAdapter(Context context, List<ProductResponse.Datum> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_prodcut, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductResponse.Datum item = productList.get(position);

        holder.productName.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            Fragment prev = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            EditDialog editDialog = EditDialog.newInstance(item.getId());
            editDialog.setCancelable(false);
            editDialog.show(ft, "dialog");
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView productName;
        private AppCompatImageView productShare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productname);
            productShare = itemView.findViewById(R.id.productshare);
        }
    }

    public static class EditDialog extends DialogFragment {

        private static final String ARG_PARAM = "pid";
        private String pid, doctorid;
        private Loader loader;
        private Snack snack;
        private AppCompatEditText cusMobileNo, cusTxtMsg;
        private NoboButton btnClose, btnSend;

        public EditDialog() {
        }

        public static EditDialog newInstance(String cid) {
            EditDialog frag = new EditDialog();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM, cid);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
            if (getArguments() != null) {
                pid = getArguments().getString(ARG_PARAM);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_edit_customer, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            loader = new Loader(getActivity());
            snack = new Snack(getActivity());

            doctorid = Prefs.getString("e_id", null);

            cusMobileNo = view.findViewById(R.id.cus_mobileno);
            cusTxtMsg = view.findViewById(R.id.cus_txtmsg);
            btnClose = view.findViewById(R.id.cus_close_btn);
            btnSend = view.findViewById(R.id.cus_send_btn);

            btnClose.setOnClickListener(v -> {
                getDialog().dismiss();
            });

            btnSend.setOnClickListener(v -> {
                if(validateNo())
                {
                    String mobile_no = cusMobileNo.getText().toString().trim();
                    String txtmessage = cusTxtMsg.getText().toString().trim();
                    senddata(doctorid, pid, mobile_no, txtmessage);
                }
            });

        }

        private void senddata(String doctorid, String pid, String mobile_no, String txtmessage) {
            loader.show("");
            RetrofitAPI api = RetrofitBASE.getRetrofit(getActivity()).create(RetrofitAPI.class);
            Call<ShareResponse> call = api.sharevalue(doctorid, pid, mobile_no, txtmessage);
            call.enqueue(new Callback<ShareResponse>() {
                @Override
                public void onResponse(@NotNull Call<ShareResponse> call, @NotNull Response<ShareResponse> response) {
                    try {
                        if (response.isSuccessful()){
                            ShareResponse data = response.body();
                            loader.dismiss("");
                            if (data != null){
                                Boolean status = Objects.requireNonNull(data).getStatus();
                                String message = data.getMessage();
                                if (status){
                                    snack.success(message);
                                    getDialog().dismiss();
                                }else {
                                    snack.failure(message);
                                }
                            }
                        }else {
                            loader.dismiss("");
                            ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                            Toast.makeText(getContext(), errorUtils.message(), Toast.LENGTH_SHORT).show();
                            Log.e("EditCusError", Objects.requireNonNull(errorUtils.message()));
                        }
                    }catch (Exception e){
                        loader.dismiss("");
                        Log.e("EditCusException", Objects.requireNonNull(e.getMessage()));
                    }
                }
                @Override
                public void onFailure(@NotNull Call<ShareResponse> call, @NotNull Throwable t) {
                    loader.dismiss("");
                    if (t instanceof SocketTimeoutException){
                        Toast.makeText(getContext(), "Timeout, Retrying Again. Please Wait", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> senddata(doctorid, pid, mobile_no, txtmessage), 3000);
                    }else if (t instanceof UnknownHostException){
                        Toast.makeText(getContext(), "Unknown Host, Check your URL", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("EditCusFailure", Objects.requireNonNull(t.getMessage()));
                }
            });
        }

        private boolean validateNo() {
            String mobileno = cusMobileNo.getText().toString().trim();
            if (mobileno.isEmpty()) {
                Toast.makeText(getActivity(), "Mobile No Field can't be empty!..", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }
    }
}
