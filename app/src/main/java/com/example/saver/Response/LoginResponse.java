package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("email_verified_at")
        @Expose
        private Object emailVerifiedAt;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("profile_url")
        @Expose
        private Object profileUrl;
        @SerializedName("remember_token")
        @Expose
        private Object rememberToken;
        @SerializedName("social_type")
        @Expose
        private Object socialType;
        @SerializedName("social_id")
        @Expose
        private Object socialId;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(Object emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public Object getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(Object profileUrl) {
            this.profileUrl = profileUrl;
        }

        public Object getRememberToken() {
            return rememberToken;
        }

        public void setRememberToken(Object rememberToken) {
            this.rememberToken = rememberToken;
        }

        public Object getSocialType() {
            return socialType;
        }

        public void setSocialType(Object socialType) {
            this.socialType = socialType;
        }

        public Object getSocialId() {
            return socialId;
        }

        public void setSocialId(Object socialId) {
            this.socialId = socialId;
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
