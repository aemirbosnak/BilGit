package com.example.trybil.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trybil.model.MainRepository;
import com.example.trybil.model.User;


public class MainViewModel extends AndroidViewModel {
    MainRepository mainRepository;
    MutableLiveData<User> user;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance(application);
        user = mainRepository.getUser();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
