package com.malik.roomdeneme3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CheckmarkViewModel extends AndroidViewModel {
    private CheckmarkRepository checkmarkRepository;
    private LiveData<List<Checkmark>> allCheckmark;

    public CheckmarkViewModel(@NonNull Application application) {
        super(application);
        checkmarkRepository = new CheckmarkRepository(application);
        allCheckmark = checkmarkRepository.getAllCheckmarks();
    }

    public void insert(Checkmark checkmark){
        checkmarkRepository.insert(checkmark);
    }

    public LiveData<List<Checkmark>> getHobbyCheckmarks(HobbyCheckmark hobby){
        return checkmarkRepository.getHobbyCheckmarks(hobby);
    }



    public LiveData<List<Checkmark>> getAllCheckmarks(){
        return allCheckmark;
    }
}
