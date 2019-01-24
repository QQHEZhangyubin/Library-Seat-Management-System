package com.example.a13608.tab01.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 13608 on 2018/5/28.
 */

public class Qiandao extends LitePalSupport {
    private String year;
    private String month;
    private String day;

    private String Xingqi;
    private String Time;
    private String room;
    private String seatid;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeatid() {
        return seatid;
    }

    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getXingqi() {
        return Xingqi;
    }

    public void setXingqi(String xingqi) {
        Xingqi = xingqi;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}
