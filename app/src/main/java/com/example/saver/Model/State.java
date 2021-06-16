package com.example.saver.Model;

public class State {

    private String stateid, statename, scountryid, sstatus;

    public State(String stateid, String statename, String scountryid, String sstatus) {
        this.stateid = stateid;
        this.statename = statename;
        this.scountryid = scountryid;
        this.sstatus = sstatus;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getScountryid() {
        return scountryid;
    }

    public void setScountryid(String scountryid) {
        this.scountryid = scountryid;
    }

    public String getSstatus() {
        return sstatus;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

}
