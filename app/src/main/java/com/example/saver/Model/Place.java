package com.example.saver.Model;

public class Place {

    private String placeid, placename;

    public Place(String placeid, String placename) {
        this.placeid = placeid;
        this.placename = placename;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }
}
