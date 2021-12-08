package com.example.trybil.model;

import android.media.Image;

public class Place {
    private String name;
    private String email;
    private String password;
    private int capacity;
    private String address;
    private String phone;
    private Image photo;
    private User[] people;
    private String category;

    //Location data of place object
    private double latitude;
    private double longitude;
    private double radius;

    /*
        We should have a place array/arraylist on mainactivity
        and define location when constructing object.
     */
    public Place(String password, String email, double lat, double lon, double r){
        latitude = lat;
        longitude = lon;
        radius = r;
        this.password = password;
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber(){
        return people.length;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public User[] getPeople() {
        return people;
    }

    public void setPeople(User[] people) {
        this.people = people;
    }

    //add five stars thing to somewhere

    /**
     * Show the graph of crowdedness
     */

    /**
     * Show the friends who there are
     */

    /**
     *
     */

    /**
     * This method add new person to the array
     */
    public void addPeople(){}
}