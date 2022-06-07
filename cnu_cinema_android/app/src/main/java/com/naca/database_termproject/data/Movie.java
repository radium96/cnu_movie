package com.naca.database_termproject.data;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
    String mid;
    String title;
    String openDay;
    String director;
    String rating;
    String length;

    public Movie(){

    }

    public Movie(String mid, String title, String openDay, String director, String rating, String length) {
        this.mid = mid;
        this.title = title;
        String[] strs = openDay.split(" ");
        this.openDay = strs[0];
        this.director = director;
        this.rating = rating;
        this.length = length;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}
