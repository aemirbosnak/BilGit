package com.example.trybil.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trybil.model.AuthRepository;
import com.google.firebase.auth.FirebaseUser;


public class AuthViewModel extends AndroidViewModel {
    AuthRepository authRepository;
    MutableLiveData<FirebaseUser> userData;
    MutableLiveData<Boolean> loggedStatus;

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userData = authRepository.getFirebaseUserMutableLiveData();
        loggedStatus = authRepository.getUserLoggedMutableLiveData();
    }

    public void register(String email , String pass){
        authRepository.register(email, pass);
    }
    public void signIn(String email , String pass){
        authRepository.login(email, pass);
    }
    public void signOut(){
        authRepository.signOut();
    }
}
