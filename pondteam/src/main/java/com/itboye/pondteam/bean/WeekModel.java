package com.itboye.pondteam.bean;

/**
 * Created by Mr.w on 2017/5/14.
 */

public  class WeekModel implements Comparable<WeekModel>{
    public int id;
    private String week;
    private String date;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    @Override
    public int compareTo(WeekModel arg0) {
        // TODO Auto-generated method stub
        return Integer.valueOf(id).compareTo(Integer.valueOf(arg0.id));
    }
}

