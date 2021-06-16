package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountResponse {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bookingToday")
    @Expose
    private String bookingToday;
    @SerializedName("booking")
    @Expose
    private String booking;
    @SerializedName("customer")
    @Expose
    private String customer;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookingToday() {
        return bookingToday;
    }

    public void setBookingToday(String bookingToday) {
        this.bookingToday = bookingToday;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

}
