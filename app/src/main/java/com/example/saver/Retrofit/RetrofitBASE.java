package com.example.saver.Retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import thebat.lib.validutil.ValidUtils;

public class RetrofitBASE {

    public static Retrofit retrofit = null;
    private final static long CACHE_SIZE = 10 * 1024 * 1024; // 10MB Cache size

    private static OkHttpClient buildClient(Context context) {
        // Build interceptor
        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
            Response originalResponse = chain.proceed(chain.request());
            if (ValidUtils.isNetworkAvailable(context)) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        return new OkHttpClient
                .Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .build();
    }

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .client(buildClient(context))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.baseUrl("http://192.168.1.46/saver/api/")
                    //.baseUrl("http://192.168.43.76/saver/api/")
                    .baseUrl("https://o2saver.com/api/")
                    .build();
        }
        return retrofit;
    }

}
