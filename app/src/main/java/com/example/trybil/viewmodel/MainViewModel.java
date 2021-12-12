package com.example.trybil.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trybil.model.MainRepository;
import com.example.trybil.model.User;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    MainRepository mainRepository;
    MutableLiveData<User> user;
    MutableLiveData<ArrayList<String>> places;
    MutableLiveData<ArrayList<Integer>> location;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance(application);
        user = mainRepository.getUser();
        places = mainRepository.getPlaces();
        location = mainRepository.getLocation();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<ArrayList<String>> getPlaces() {
        return places;
    }

    public MutableLiveData<ArrayList<Integer>> getLocation() {
        return location;
    }
}
