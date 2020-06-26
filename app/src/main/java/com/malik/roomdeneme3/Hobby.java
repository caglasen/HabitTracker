package com.malik.roomdeneme3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "hobby_table")
public class Hobby {


    @PrimaryKey(autoGenerate = true)
protected int id;

    private String title;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private String description;
    private String time;

    private int priority;
    //new feature start here
    private boolean routine;
    private boolean positive;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean trs;
    private boolean fri;
    private boolean sat;
    private boolean sun;
    private int clr;

    public int getClr() {
        return clr;
    }

    public void setClr(int clr) {
        this.clr = clr;
    }
//private String date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRoutine(boolean routine) {
        this.routine = routine;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public void setTrs(boolean trs) {
        this.trs = trs;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

  /*  public void setClr(int clr) {
        this.clr = clr;
    }

    public void setDate(String date) {
        this.date = date;
    }*/

    public boolean isRoutine() {
        return routine;
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean isMon() {
        return mon;
    }

    public boolean isTue() {
        return tue;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isTrs() {
        return trs;
    }

    public boolean isFri() {
        return fri;
    }

    public boolean isSat() {
        return sat;
    }

    public boolean isSun() {
        return sun;
    }

    /*public int getClr() {
        return clr;
    }

    public String getDate() {
        return date;
    }
*/
     public Hobby(String title, String description, String time, int priority, boolean routine, boolean positive, boolean mon, boolean tue, boolean wed, boolean trs, boolean fri, boolean sat, boolean sun,int total,int clr) {
        this.title = title;
        this.clr=clr;
        this.total =total;
        this.description = description;
        this.time = time;
        this.priority = priority;
        this.routine = routine;
        this.positive = positive;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.trs = trs;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;

    }

    //end here :)

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }
/*
    public Hobby(String title, String description,String time, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.time=time;
    }*/




}
