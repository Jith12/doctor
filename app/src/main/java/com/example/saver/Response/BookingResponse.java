package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {

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
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("typeId")
        @Expose
        private String typeId;
        @SerializedName("scheduleId")
        @Expose
        private String scheduleId;
        @SerializedName("customerId")
        @Expose
        private String customerId;
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("customerEmail")
        @Expose
        private String customerEmail;
        @SerializedName("customerMobile")
        @Expose
        private String customerMobile;
        @SerializedName("customerAlternateMobile")
        @Expose
        private String customerAlternateMobile;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }

        public String getCustomerMobile() {
            return customerMobile;
        }

        public void setCustomerMobile(String customerMobile) {
            this.customerMobile = customerMobile;
        }

        public String getCustomerAlternateMobile() {
            return customerAlternateMobile;
        }

        public void setCustomerAlternateMobile(String customerAlternateMobile) {
            this.customerAlternateMobile = customerAlternateMobile;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
