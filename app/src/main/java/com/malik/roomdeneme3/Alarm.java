package com.malik.roomdeneme3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "alarm_table")
public class Alarm {


    @PrimaryKey(autoGenerate = true)
protected int id;

    private String date;

     private String name;

    public int getPendNum() {
        return pendNum;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setPendNum(int pendNum) {
        this.pendNum = pendNum;
    }

    private int pendNum;
    private String dateTime;

    public Alarm(String name, String date, int pendNum, String dateTime) {
        this.date = date;
        this.name = name;
        this.pendNum=pendNum;
        this.dateTime=dateTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

       public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }




}
