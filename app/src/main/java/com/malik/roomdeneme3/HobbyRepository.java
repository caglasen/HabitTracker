package com.malik.roomdeneme3;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

public class HobbyRepository {

    private HobbyDao hobbyDao;
    private LiveData<List<Hobby>> allHobbies;
    private LiveData<List<HobbyCheckmark>> test;


    public HobbyRepository(Application application) {
        HobbyDatabase database = HobbyDatabase.getInstance(application);
        hobbyDao = database.hobbyDao();
        allHobbies = hobbyDao.getAllHobbies();
        test = hobbyDao.getHobbyandCheckmarks();
    }


    public void insert(Hobby hobby){
        new InsertHobbyAsyncTask(hobbyDao).execute(hobby);
    }

    public void update(Hobby hobby){

        new UpdateHobbyAsyncTask(hobbyDao).execute(hobby);
    }

    public void delete(Hobby hobby){
        new DeleteHobbyAsyncTask(hobbyDao).execute(hobby);
    }

    public void delete(HobbyCheckmark hobby){
        new DeleteHobbyByCHAsyncTask(hobbyDao).execute(new SimpleSQLiteQuery("DELETE FROM HOBBY_TABLE WHERE ID = ?" , new Object[] {hobby.id}));
        getHobbyandCheckmark();
        /*
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                hobbyDao.deleteByHobbyCheckmark(new SimpleSQLiteQuery("DELETE FROM HOBBY_TABLE WHERE ID = ?" , new Object[] {hobby.id}));
            }
        });
        */

    }


    public void deleteAllHobbies(){
        new DeleteAllHobbiesAsyncTask(hobbyDao).execute();
    }

    public LiveData<List<Hobby>> getAllHobbies() {
        return allHobbies;
    }
    public LiveData<List<HobbyCheckmark>> getHobbyandCheckmark() {
        test = hobbyDao.getHobbyandCheckmarks();
        return  test;
    }

    private static class DeleteHobbyByCHAsyncTask extends AsyncTask<SimpleSQLiteQuery,Void,Void>{

        private HobbyDao hobbyDao;
        private DeleteHobbyByCHAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }


        @Override
        protected Void doInBackground(SimpleSQLiteQuery... queries) {
            hobbyDao.deleteByHobbyCheckmark(queries[0]);
            return null;
        }
    }

    private static class InsertHobbyAsyncTask extends AsyncTask<Hobby,Void,Void>{

        private HobbyDao hobbyDao;
        private InsertHobbyAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }


        @Override
        protected Void doInBackground(Hobby... hobbies) {
            hobbyDao.insert(hobbies[0]);
            return null;
        }
    }

    private static class DeleteAllHobbiesAsyncTask extends AsyncTask<Void,Void,Void>{

        private HobbyDao hobbyDao;
        private DeleteAllHobbiesAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            hobbyDao.deleteAllHobbies();
            return null;
        }
    }

    private static class UpdateHobbyAsyncTask extends AsyncTask<Hobby,Void,Void>{

        private HobbyDao hobbyDao;
        private UpdateHobbyAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }


        @Override
        protected Void doInBackground(Hobby... hobbies) {
            hobbyDao.update(hobbies[0]);
            return null;
        }
    }
    private static class DeleteHobbyAsyncTask extends AsyncTask<Hobby,Void,Void>{

        private HobbyDao hobbyDao;
        private DeleteHobbyAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }


        @Override
        protected Void doInBackground(Hobby... hobbies) {
            hobbyDao.delete(hobbies[0]);
            return null;
        }
    }
    private static class DeleteHobbyChAsyncTask extends AsyncTask<Hobby,Void,Void>{

        private HobbyDao hobbyDao;
        private DeleteHobbyChAsyncTask(HobbyDao hobbyDao){
            this.hobbyDao = hobbyDao;
        }


        @Override
        protected Void doInBackground(Hobby... hobbies) {
            hobbyDao.delete(hobbies[0]);
            return null;
        }
    }




}
