package com.naca.database_termproject.data;

public class Ticket {
    String id;
    String title;
    String tname;
    String sdate;
    String stime;
    String seat;
    String status;
    String rcdate;
    String cname;

    public Ticket(String id, String title, String tname, String sdatetime, String seat, String status, String rcdate, String cname){
        this.id = id;
        this.title = title;
        this.tname = tname;
        String[] sdt = sdatetime.split(" ");
        this.sdate = sdt[0];
        this.stime = sdt[1];
        this.seat = seat;
        if(status.equals("W")){
            this.status = "관람 완료";
        } else if (status.equals("C")){
            this.status = "예매 취소";
        } else if (status.equals("R")){
            this.status = "예매 완료";
        }
        String[] rc = rcdate.split(" ");
        this.rcdate = rc[0];
        this.cname = cname;
    }

    public Ticket(String id, String title, String tname, String sdatetime, String seat, String status, String rcdate){
        this.id = id;
        this.title = title;
        this.tname = tname;
        String[] sdt = sdatetime.split(" ");
        this.sdate = sdt[0];
        this.stime = sdt[1];
        this.seat = seat;
        if(status.equals("W")){
            this.status = "관람 완료";
        } else if (status.equals("C")){
            this.status = "예매 취소";
        } else if (status.equals("R")){
            this.status = "예매 완료";
        }
        String[] rc = rcdate.split(" ");
        this.rcdate = rc[0];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRcdate() {
        return rcdate;
    }

    public void setRcdate(String rcdate) {
        this.rcdate = rcdate;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
