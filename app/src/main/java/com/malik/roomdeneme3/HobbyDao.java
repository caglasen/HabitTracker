package com.malik.roomdeneme3;


import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface HobbyDao {

    @Insert
    void insert(Hobby hobby);

    @Update
    void update(Hobby hobby);

    @Delete
    void delete(Hobby hobby);

    @RawQuery()
    int deleteByHobbyCheckmark(SimpleSQLiteQuery query);

    @Query("DELETE FROM hobby_table")
    void deleteAllHobbies();

    @Query("SELECT ht.id, ht.title, ht.description, ht.clr, ht.priority, ht.time, ht.clr, ht.routine,ht.positive,ht.mon,ht.tue,ht.wed,ht.trs,ht.fri,ht.sat,ht.sun, sum(ct.numberOfMinutesDone) as duration FROM hobby_table HT LEFT JOIN CHECKMARK_TABLE CT ON HT.ID=CT.HOBBY_ID group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16 ORDER BY priority ASC")
    LiveData<List<HobbyCheckmark>> getHobbyandCheckmarks();

    @Query("SELECT * FROM hobby_table ORDER BY priority DESC")
    LiveData<List<Hobby>> getAllHobbies();

}
