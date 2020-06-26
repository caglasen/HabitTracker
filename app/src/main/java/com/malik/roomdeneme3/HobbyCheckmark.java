package com.malik.roomdeneme3;

public class HobbyCheckmark
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected int id;
    protected String title;
    protected String description;
    protected int duration;
    protected int priority;
    protected int clr;
    protected String time;

    protected boolean routine;
    protected boolean positive;
    protected boolean mon;
    protected boolean tue;
    protected boolean wed;
    protected boolean trs;
    protected boolean fri;
    protected boolean sat;
    protected boolean sun;


}
