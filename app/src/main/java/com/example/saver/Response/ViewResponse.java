package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("hospital_id")
        @Expose
        private String hospitalId;
        @SerializedName("doctor_category_id")
        @Expose
        private String doctorCategoryId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("sex")
        @Expose
        private String sex;
        @SerializedName("landline")
        @Expose
        private String landline;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("doctor_fee")
        @Expose
        private String doctorFee;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("doctorType")
        @Expose
        private String doctorType;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("qualification")
        @Expose
        private String qualification;
        @SerializedName("approve_status")
        @Expose
        private String approveStatus;
        @SerializedName("delete_status")
        @Expose
        private String deleteStatus;
        @SerializedName("email_status")
        @Expose
        private String emailStatus;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("place_name")
        @Expose
        private String placeName;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("place_id")
        @Expose
        private String placeId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getDoctorCategoryId() {
            return doctorCategoryId;
        }

        public void setDoctorCategoryId(String doctorCategoryId) {
            this.doctorCategoryId = doctorCategoryId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLandline() {
            return landline;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getDoctorFee() {
            return doctorFee;
        }

        public void setDoctorFee(String doctorFee) {
            this.doctorFee = doctorFee;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDoctorType() {
            return doctorType;
        }

        public void setDoctorType(String doctorType) {
            this.doctorType = doctorType;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(String approveStatus) {
            this.approveStatus = approveStatus;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getEmailStatus() {
            return emailStatus;
        }

        public void setEmailStatus(String emailStatus) {
            this.emailStatus = emailStatus;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
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

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

    }
}
