package com.example.saver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.LoginResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;
import thebat.lib.validutil.ValidUtils;

public class LoginActivity extends AppCompatActivity {

    private TextFieldBoxes logEmailtxt, logPasstxt;
    private ExtendedEditText logEmailid, logPassword;
    private NoboButton btnlogSignup, btnlogLogin;
    private AppCompatTextView logForgetpass;
    private Loader loader;
    private Snack snack;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loader = new Loader(this);
        snack = new Snack(this);

        boolean check = Prefs.getBoolean("logged_in", false);

        if(check)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Animatoo.animateFade(LoginActivity.this);
            finish();
        }

        logEmailtxt = findViewById(R.id.log_emailtxt);
        logPasstxt = findViewById(R.id.log_passtxt);
        logEmailid = findViewById(R.id.log_emailid);
        logPassword = findViewById(R.id.log_password);
        //btnlogSignup = findViewById(R.id.log_signup);
        btnlogLogin = findViewById(R.id.log_login);
        logForgetpass = findViewById(R.id.log_forgetpass);


        btnlogLogin.setOnClickListener(v -> {
            if (ValidUtils.isNetworkAvailable(this)) {
                if(ValidateEmail())
                {
                    if(ValidatePass())
                    {
                        String regemail = logEmailid.getText().toString().trim();
                        String regpass = logPassword.getText().toString().trim();

                        login(regemail, regpass);
                    }
                }
            }else {
                snack.network("Check Your Network Connection");
            }
        });
    }

    private void login(String regemail, String regpass) {
        loader.show("");

        RetrofitAPI api = RetrofitBASE.getRetrofit(this).create(RetrofitAPI.class);
        Call<LoginResponse> call = api.login(regemail, regpass);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                try {
                    if (response.isSuccessful()){
                        LoginResponse data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){
                                LoginResponse.Data result = data.getData();
                                snack.success(message);
                                Prefs.putBoolean("logged_in", true);
                                Prefs.putString("e_id", result.getId());
                                Prefs.putString("e_name", result.getName());
                                new Handler().postDelayed(() -> {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    Animatoo.animateFade(LoginActivity.this);
                                    finish();
                                }, 1000);
                            }else {
                                snack.failure(message);
                            }
                        }
                        else
                        {
                            snack.failure(response.message());
                        }
                    }
                    else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.failure(errorUtils.message());
                        Log.e("LoginError", Objects.requireNonNull(errorUtils.message()));
                    }
                }
                catch (Exception e)
                {
                    loader.dismiss("");
                    Log.e("LoginException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> login(regemail,regpass), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("LoginFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private boolean ValidatePass() {
        String pass = logPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            logPasstxt.setError("Field can't be Empty", true);
            return false;
        } else {
            return true;
        }
    }

    private boolean ValidateEmail() {
        String email = logEmailid.getText().toString().trim();
        if (email.isEmpty()) {
            logEmailtxt.setError("Field can't be Empty", true);
            return false;
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            logEmailtxt.setError("Invalid Email Pattern", true);
            return false;
        } else {
            return true;
        }
    }
}