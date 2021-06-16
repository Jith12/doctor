package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingdateResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("bookingno")
        @Expose
        private String bookingno;
        @SerializedName("cusname")
        @Expose
        private String cusname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("cusemail")
        @Expose
        private String cusemail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBookingno() {
            return bookingno;
        }

        public void setBookingno(String bookingno) {
            this.bookingno = bookingno;
        }

        public String getCusname() {
            return cusname;
        }

        public void setCusname(String cusname) {
            this.cusname = cusname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCusemail() {
            return cusemail;
        }

        public void setCusemail(String cusemail) {
            this.cusemail = cusemail;
        }

    }
}
