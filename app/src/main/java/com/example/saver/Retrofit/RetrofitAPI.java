package com.example.saver.Retrofit;

import com.example.saver.Response.AddScheduleResponse;
import com.example.saver.Response.AppointmentResponse;
import com.example.saver.Response.BookingAllResponse;
import com.example.saver.Response.BookingResponse;
import com.example.saver.Response.BookingdateResponse;
import com.example.saver.Response.CountResponse;
import com.example.saver.Response.CustomerAllResponse;
import com.example.saver.Response.CustomerResponse;
import com.example.saver.Response.DoctorResponse;
import com.example.saver.Response.LocationResponse;
import com.example.saver.Response.LoginResponse;
import com.example.saver.Response.PlaceResponse;
import com.example.saver.Response.SaveProfile;
import com.example.saver.Response.ViewResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("appointmentlist")
    Call<AppointmentResponse> appointmentlist(
            @Field("doctorid") String doctorid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("doctorlist")
    Call<DoctorResponse> doctorlist(
            @Field("doctorid") String doctorid,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("doctoradd")
    Call<AddScheduleResponse> addschedule(
            @Field("doctorid") String doctorid,
            @Field("date") String date,
            @Field("starttime") String starttime,
            @Field("endtime") String endtime
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("dashboard")
    Call<CountResponse> count(
            @Field("doctorid") String doctorid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("customerlist")
    Call<CustomerResponse> customers(
            @Field("doctorid") String doctorid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("bookinglist")
    Call<BookingResponse> bookings(
            @Field("doctorid") String doctorid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("bookinglist_date")
    Call<BookingdateResponse> bookingsdates(
            @Field("doctorid") String doctorid,
            @Field("date") String date
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("bookinglistall")
    Call<BookingAllResponse> bookinglistall(
            @Field("doctorid") String doctorid,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("customerlistall")
    Call<CustomerAllResponse> customerlistall(
            @Field("doctorid") String doctorid,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @GET("locations")
    Call<LocationResponse> locations();

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @GET("locationsplace")
    Call<PlaceResponse> places();

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @FormUrlEncoded
    @POST("viewprofile")
    Call<ViewResponse> viewprofile(
            @Field("doctorid") String doctorid
    );

    @Headers({"Authorization:Basic YW5kcm9pZDphcGs=", "x-api-key:saver"})
    @Multipart
    @POST("saveprofile")
    Call<SaveProfile> saveprofile(
            @Part("doctorid") RequestBody doctorid,
            @Part("drName") RequestBody drName,
            @Part("drEmailid") RequestBody drEmailid,
            @Part("drMobileno") RequestBody drMobileno,
            @Part("drLandlineNo") RequestBody drLandlineNo,
            @Part("Location") RequestBody Location,
            @Part("drAddress1") RequestBody drAddress1,
            @Part("drAddress2") RequestBody drAddress2,
            @Part("Country") RequestBody Country,
            @Part("State") RequestBody State,
            @Part("City") RequestBody City,
            @Part("drZipcode") RequestBody drZipcode,
            @Part("drExperience") RequestBody drExperience,
            @Part("drFees") RequestBody drFees,
            @Part MultipartBody.Part[] image
    );

}
