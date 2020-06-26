package com.malik.roomdeneme3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {

    private static AlarmDatabase instance;

    public abstract AlarmDao alarmDao();

    public static synchronized AlarmDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AlarmDatabase.class, "alarm_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
return instance;

    }


    private static Callback roomCallback = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private AlarmDao alarmDao;
        private PopulateDbAsyncTask(AlarmDatabase db){
            alarmDao = db.alarmDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
    /*       alarmDao.insert(new Alarm()));
            alarmDao.insert(new Alarm("title 2","Description 2","35", 1,true,false,true,true,false,false,false,true,true,0,Color.parseColor("#FF9800")));
            alarmDao.insert(new Alarm("title 3","Description 3","45", 1,true,false,true,true,false,false,false,true,true,0,Color.parseColor("#3F51B5")));
         */   return null;
        }
    }
}
