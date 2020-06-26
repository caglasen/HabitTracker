package com.malik.roomdeneme3;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "checkmark_table")
public class Checkmark {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    @ForeignKey(entity = Hobby.class, parentColumns ="id", childColumns ="hobby_id" )
    protected int hobby_id;

    protected String date;
    protected int numberOfMinutesDone;

    public Checkmark(){}

    public Checkmark(int id, int hobby_id, int numberOfMinutesDone) {
        this.id = id;
        this.hobby_id = hobby_id;
        //this.date = new Date(); // Current date
        this.numberOfMinutesDone = numberOfMinutesDone;
    }

    public Date getDate() throws ParseException {
        //return java.sql.Date.valueOf(date);
        SimpleDateFormat sdf= new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        return sdf.parse(date);
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    public int getNumberOfMinutesDone() {
        return numberOfMinutesDone;
    }

    public void setNumberOfMinutesDone(int numberOfMinutesDone) {
        this.numberOfMinutesDone = numberOfMinutesDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(int hobby_id) {
        this.hobby_id = hobby_id;
    }

}
