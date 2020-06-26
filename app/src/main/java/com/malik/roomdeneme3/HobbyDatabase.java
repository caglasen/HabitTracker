package com.malik.roomdeneme3;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Hobby.class, Checkmark.class}, version = 2, exportSchema = false)
public abstract class HobbyDatabase extends RoomDatabase {

    private static HobbyDatabase instance;

    public abstract HobbyDao hobbyDao();
    public abstract CheckmarkDao checkmarkDao();

    public static synchronized HobbyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HobbyDatabase.class, "hobby_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
    return instance;

    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private HobbyDao hobbyDao;
        private CheckmarkDao checkmarkDao;
        private PopulateDbAsyncTask(HobbyDatabase db){
            hobbyDao = db.hobbyDao();
            checkmarkDao = db.checkmarkDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
           hobbyDao.insert(new Hobby("title 1","Description 1","25", 1,true,false,true,true,false,false,false,true,true,0, Color.parseColor("#3F51B5")));
            hobbyDao.insert(new Hobby("title 2","Description 2","35", 1,true,false,true,true,false,false,false,true,true,0,Color.parseColor("#FF9800")));
            hobbyDao.insert(new Hobby("title 3","Description 3","45", 1,true,false,true,true,false,false,false,true,true,0,Color.parseColor("#3F51B5")));
            return null;
        }
    }
}
