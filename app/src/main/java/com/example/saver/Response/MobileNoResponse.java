package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MobileNoResponse {

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
        @SerializedName("cusMobileNo")
        @Expose
        private String cusMobileNo;
        @SerializedName("cusId")
        @Expose
        private String cusId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCusMobileNo() {
            return cusMobileNo;
        }

        public void setCusMobileNo(String cusMobileNo) {
            this.cusMobileNo = cusMobileNo;
        }

        public String getCusId() {
            return cusId;
        }

        public void setCusId(String cusId) {
            this.cusId = cusId;
        }

    }
}
