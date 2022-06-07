package com.naca.database_termproject.data;

import java.io.Serializable;

public class Schedule implements Serializable {
    String sdate;
    String stime;
    String tname;
    String seats;
    String mid;
    String sid;
    String title;

    public Schedule(String sdatetime, String tname, String seats, String mid, String sid, String title) {
        String[] str = sdatetime.split(" ");
        this.sdate = str[0];
        this.stime = str[1];
        this.tname = tname;
        this.seats = seats;
        this.mid = mid;
        this.sid = sid;
        this.title = title;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
