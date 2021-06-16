package com.example.saver.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("country")
    @Expose
    private List<Country> country = null;
    @SerializedName("state")
    @Expose
    private List<State> state = null;
    @SerializedName("city")
    @Expose
    private List<City> city = null;
    @SerializedName("assembely")
    @Expose
    private List<Assembely> assembely = null;

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

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Assembely> getAssembely() {
        return assembely;
    }

    public void setAssembely(List<Assembely> assembely) {
        this.assembely = assembely;
    }

    public class Country {

        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("status")
        @Expose
        private String status;

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class State {

        @SerializedName("sstate_id")
        @Expose
        private String sstateId;
        @SerializedName("sstate_name")
        @Expose
        private String sstateName;
        @SerializedName("scountry_id")
        @Expose
        private String scountryId;
        @SerializedName("sstatus")
        @Expose
        private String sstatus;

        public String getSstateId() {
            return sstateId;
        }

        public void setSstateId(String sstateId) {
            this.sstateId = sstateId;
        }

        public String getSstateName() {
            return sstateName;
        }

        public void setSstateName(String sstateName) {
            this.sstateName = sstateName;
        }

        public String getScountryId() {
            return scountryId;
        }

        public void setScountryId(String scountryId) {
            this.scountryId = scountryId;
        }

        public String getSstatus() {
            return sstatus;
        }

        public void setSstatus(String sstatus) {
            this.sstatus = sstatus;
        }

    }

    public class City {

        @SerializedName("ccity_id")
        @Expose
        private String ccityId;
        @SerializedName("ccity_name")
        @Expose
        private String ccityName;
        @SerializedName("cstate_id")
        @Expose
        private String cstateId;

        public String getCcityId() {
            return ccityId;
        }

        public void setCcityId(String ccityId) {
            this.ccityId = ccityId;
        }

        public String getCcityName() {
            return ccityName;
        }

        public void setCcityName(String ccityName) {
            this.ccityName = ccityName;
        }

        public String getCstateId() {
            return cstateId;
        }

        public void setCstateId(String cstateId) {
            this.cstateId = cstateId;
        }

    }

    public class Assembely {

        @SerializedName("aassembly_id")
        @Expose
        private String aassemblyId;
        @SerializedName("aassembly_name")
        @Expose
        private String aassemblyName;
        @SerializedName("acountry_id")
        @Expose
        private String acountryId;
        @SerializedName("astate_id")
        @Expose
        private String astateId;
        @SerializedName("acity_id")
        @Expose
        private String acityId;
        @SerializedName("astatus")
        @Expose
        private String astatus;

        public String getAassemblyId() {
            return aassemblyId;
        }

        public void setAassemblyId(String aassemblyId) {
            this.aassemblyId = aassemblyId;
        }

        public String getAassemblyName() {
            return aassemblyName;
        }

        public void setAassemblyName(String aassemblyName) {
            this.aassemblyName = aassemblyName;
        }

        public String getAcountryId() {
            return acountryId;
        }

        public void setAcountryId(String acountryId) {
            this.acountryId = acountryId;
        }

        public String getAstateId() {
            return astateId;
        }

        public void setAstateId(String astateId) {
            this.astateId = astateId;
        }

        public String getAcityId() {
            return acityId;
        }

        public void setAcityId(String acityId) {
            this.acityId = acityId;
        }

        public String getAstatus() {
            return astatus;
        }

        public void setAstatus(String astatus) {
            this.astatus = astatus;
        }

    }
}
