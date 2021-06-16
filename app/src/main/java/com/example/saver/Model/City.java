package com.example.saver.Model;

public class City {

    private String cityid, cityname, cstateid;

    public City(String cityid, String cityname, String cstateid) {
        this.cityid = cityid;
        this.cityname = cityname;
        this.cstateid = cstateid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCstateid() {
        return cstateid;
    }

    public void setCstateid(String cstateid) {
        this.cstateid = cstateid;
    }
}
