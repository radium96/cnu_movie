package com.naca.database_termproject.data;

import java.io.Serializable;

// 영화 상영 스케쥴의 정보를 저장하는 Model.
public class Schedule implements Serializable {
    String sdate; // 스케쥴 날짜
    String stime; // 스케쥴 시간
    String tname; // 상영관 이름
    String seats; // 상영관의 전체 좌석 수
    String disable; // 스케쥴에서 이미 예약되어있는 좌석의 수
    String mid; // 스케쥴에서 상영하는 영화의 id
    String sid; // 데이터베이스상 스케쥴의 id
    String title; // 영화의 제목

    public Schedule(String sdatetime, String tname, String seats, String disable,String mid, String sid, String title) {
        String[] str = sdatetime.split(" ");
        this.sdate = str[0];
        this.stime = str[1];
        this.tname = tname;
        this.seats = seats;
        this.disable = disable;
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

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
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

    @Override
    public String toString() {
        return "Schedule{" +
                "sdate='" + sdate + '\'' +
                ", stime='" + stime + '\'' +
                ", tname='" + tname + '\'' +
                ", seats='" + seats + '\'' +
                ", disable='" + disable + '\'' +
                ", mid='" + mid + '\'' +
                ", sid='" + sid + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
