package com.malik.roomdeneme3;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CheckmarkRepository {

    private CheckmarkDao checkmarkDao;
    private LiveData<List<Checkmark>> allCheckmarks;
    private LiveData<List<Checkmark>> getHobbyCheckmarks;


    public CheckmarkRepository(Application application) {
        HobbyDatabase database = HobbyDatabase.getInstance(application);
        checkmarkDao = database.checkmarkDao();
        allCheckmarks = checkmarkDao.getAllCheckmarks();
        //testtest = checkmarkDao.getAllCheckmarks().getValue();
    }

    public void insert(Checkmark checkMark){
        new InsertCheckmarkAsyncTask(checkmarkDao).execute(checkMark);
    }

    public LiveData<List<Checkmark>> getHobbyCheckmarks(HobbyCheckmark hobby){
         getHobbyCheckmarks = checkmarkDao.getHobbyCheckmarks(hobby.id);
         return getHobbyCheckmarks;
    }


    public LiveData<List<Checkmark>> getAllCheckmarks() {
        return allCheckmarks;
    }

    private static class InsertCheckmarkAsyncTask extends AsyncTask<Checkmark,Void,Void> {

        private CheckmarkDao checkmarkDao;
        private InsertCheckmarkAsyncTask(CheckmarkDao checkmarkDao){
            this.checkmarkDao = checkmarkDao;
        }

        @Override
        protected Void doInBackground(Checkmark... checkmarks) {
            checkmarkDao.insert(checkmarks[0]);
            return null;
        }
    }

    private static class GetHobbyCheckmarkAsyncTask extends AsyncTask<HobbyCheckmark,Void,Void> {

        private CheckmarkDao checkmarkDao;
        private GetHobbyCheckmarkAsyncTask(CheckmarkDao checkmarkDao){
            this.checkmarkDao = checkmarkDao;
        }

        @Override
        protected Void doInBackground(HobbyCheckmark... hobbies) {
            checkmarkDao.getHobbyCheckmarks(hobbies[0].id);
            return null;
        }
    }

}
