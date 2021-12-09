package com.example.trybil.model;

import android.media.Image;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class Place {

    private String placeName;
    private String category;
    private int capacity;
    private String address;
    private String phoneNumber;
    private Image placePhoto;
    private ArrayList<User> friendsInPlace;
    private double radius;
    private DatabaseReference reference;


    //for test use
    public Place(String placeName, String category, double radius) {
        this.placeName = placeName;
        this.category = category;
        this.radius = radius;
        reference = FirebaseDatabase.getInstance().getReference().child("UserInPlace");
        //add listener to reference
    }

    public Place(String placeName, String category,  double radius, int capacity, String phoneNumber, String address, Image placePhoto) {
        this.placeName = placeName;
        this.category = category;
        this.capacity = capacity;
        this.radius = radius;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.placePhoto = placePhoto;
        friendsInPlace = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("UserInPlace");
        //add listener to reference
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getCategory() {
        return category;
    }

    public double getRadius() {
        return radius;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Image getPlacePhoto() {
        return placePhoto;
    }

    //the rating stars

    /**
     * Show the graph of the crowdedness
     */
    public void showCrowdednessGraph() {

    }

    /**
     * Show the friends of a user who are in the place
     */
    public void showFriends() {

    }
}