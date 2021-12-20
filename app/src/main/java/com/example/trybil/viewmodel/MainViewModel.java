package com.example.trybil.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.trybil.model.MainRepository;
import com.example.trybil.model.Place;
import com.example.trybil.model.User;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    MainRepository mainRepository;
    MutableLiveData<User> user;
    MutableLiveData<User> searchUser;
    MutableLiveData<Boolean> isFriend;
    MutableLiveData<ArrayList<User>> userRequest;
    MutableLiveData<Bitmap> picture;
    MutableLiveData<Bitmap> searchPic;
    MutableLiveData<ArrayList<String>> places;
    MutableLiveData<Place> place;
    MutableLiveData<ArrayList<Integer>> location;
    MutableLiveData<Integer> rating;
    MutableLiveData<ArrayList<String>> friends;
    MutableLiveData<ArrayList<User>> inPlaceFriends;
    MutableLiveData<ArrayList<String>> requests;
    MutableLiveData<ArrayList<Integer>> populations;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance(application);
        user = mainRepository.getUser();
        searchUser = mainRepository.getSearchUser();
        isFriend = mainRepository.getIsFriend();
        userRequest = mainRepository.getUserRequest();
        picture = mainRepository.getPicture();
        searchPic = mainRepository.getSearchPicture();
        places = mainRepository.getPlaces();
        place = mainRepository.getPlace();
        location = mainRepository.getLocation();
        rating = mainRepository.getRating();
        friends = mainRepository.getFriends();
        inPlaceFriends = mainRepository.getInPlaceFriends();
        requests = mainRepository.getRequests();
        populations = mainRepository.getPopulations();
    }

    public void searchUser(String username) {
        mainRepository.searchUser(username);
    }

    public void uploadPic(Uri image) {
        mainRepository.uploadPic(image);
    }

    public void addFriend() {
        mainRepository.addFriend();
    }

    public void removeFriend() {
        mainRepository.removeFriend();
    }

    public void changePlace(String name) {
        mainRepository.changePlace(name);
    }

    public void setRating(Integer rating) {
        mainRepository.setRating(rating);
    }

    public void pullInPlace() {
        mainRepository.pullInPlace();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<User> getSearchUser() {
        return searchUser;
    }

    public MutableLiveData<Boolean> getIsFriend() {
        return isFriend;
    }

    public MutableLiveData<ArrayList<User>> getUserRequest() {
        return userRequest;
    }

    public MutableLiveData<Bitmap> getPicture() {
        return picture;
    }

    public MutableLiveData<Bitmap> getSearchPic() {
        return searchPic;
    }

    public MutableLiveData<ArrayList<String>> getPlaces() {
        return places;
    }

    public MutableLiveData<Place> getPlace() { return place; }

    public MutableLiveData<ArrayList<Integer>> getLocation() {
        return location;
    }

    public MutableLiveData<Integer> getRating() {
        return rating;
    }

    public MutableLiveData<ArrayList<String>> getFriends() {
        return friends;
    }

    public MutableLiveData<ArrayList<User>> getInPlaceFriends() {
        return inPlaceFriends;
    }

    public MutableLiveData<ArrayList<String>> getRequests() {
        return requests;
    }

    public MutableLiveData<ArrayList<Integer>> getPopulations() { return populations; }
}
