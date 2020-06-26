package com.malik.roomdeneme3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CheckmarkDao {
    @Insert
    void insert(Checkmark checkMark);

    @Query("SELECT * FROM CHECKMARK_TABLE WHERE id=:id")
    LiveData<List<Checkmark>> getCheckmark(int id);

    @Query("SELECT * FROM CHECKMARK_TABLE WHERE hobby_id=:id")
    LiveData<List<Checkmark>> getHobbyCheckmarks(int id);

    @Query("SELECT * FROM checkmark_table")
    LiveData<List<Checkmark>> getAllCheckmarks();
}
