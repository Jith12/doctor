package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {

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
        @SerializedName("pharmacyId")
        @Expose
        private String pharmacyId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("priceType")
        @Expose
        private String priceType;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("isOffer")
        @Expose
        private String isOffer;
        @SerializedName("offerType")
        @Expose
        private Object offerType;
        @SerializedName("offer")
        @Expose
        private Object offer;
        @SerializedName("isGst")
        @Expose
        private String isGst;
        @SerializedName("gstPercentage")
        @Expose
        private Object gstPercentage;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("deleteStatus")
        @Expose
        private String deleteStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPharmacyId() {
            return pharmacyId;
        }

        public void setPharmacyId(String pharmacyId) {
            this.pharmacyId = pharmacyId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIsOffer() {
            return isOffer;
        }

        public void setIsOffer(String isOffer) {
            this.isOffer = isOffer;
        }

        public Object getOfferType() {
            return offerType;
        }

        public void setOfferType(Object offerType) {
            this.offerType = offerType;
        }

        public Object getOffer() {
            return offer;
        }

        public void setOffer(Object offer) {
            this.offer = offer;
        }

        public String getIsGst() {
            return isGst;
        }

        public void setIsGst(String isGst) {
            this.isGst = isGst;
        }

        public Object getGstPercentage() {
            return gstPercentage;
        }

        public void setGstPercentage(Object gstPercentage) {
            this.gstPercentage = gstPercentage;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
