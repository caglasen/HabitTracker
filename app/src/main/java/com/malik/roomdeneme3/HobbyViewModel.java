package com.malik.roomdeneme3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HobbyViewModel extends AndroidViewModel {
    private HobbyRepository repository;
    private LiveData<List<Hobby>> allHobbies;
    private LiveData<List<HobbyCheckmark>> hobbyCheckmarks;

    public HobbyViewModel(@NonNull Application application) {
        super(application);
        repository = new HobbyRepository(application);
        allHobbies = repository.getAllHobbies();
        hobbyCheckmarks = repository.getHobbyandCheckmark();
    }

    public void insert(Hobby hobby){
        repository.insert(hobby);
    }

    public void update(Hobby hobby){
        repository.update(hobby);
    }

    public void delete(Hobby hobby){

        repository.delete(hobby);
    }

    public void delete(HobbyCheckmark hobby){
        repository.delete(hobby);
    }
    public void deleteAllHobbies(){
        repository.deleteAllHobbies();
    }

    public LiveData<List<Hobby>> getAllHobbies() {
        return allHobbies;
    }
    public LiveData<List<HobbyCheckmark>> getHobbyandCheckmarks() {
        hobbyCheckmarks = repository.getHobbyandCheckmark();
        return hobbyCheckmarks;
    }
}
