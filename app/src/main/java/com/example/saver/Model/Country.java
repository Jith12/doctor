package com.example.saver.Model;

public class Country {

    private String countryid, countryname, status;

    public Country(String countryid, String countryname, String status) {
        this.countryid = countryid;
        this.countryname = countryname;
        this.status = status;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
