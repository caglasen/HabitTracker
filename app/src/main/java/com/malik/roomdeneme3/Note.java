package com.malik.roomdeneme3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_table")
public class Note {


    @PrimaryKey(autoGenerate = true)
    protected int id;

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

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    private String title;

    private String description;

    private int priority;


}


