package com.example.trybil.model;

import android.location.Location;

import java.util.ArrayList;

public class PlaceManager {
    static ArrayList<Place> places = new ArrayList<>();

    Place a = new Place( "MozartCafe", 123, 32.747971, 39.868728);

    public PlaceManager() {
        //TODO: mutable data arraylist of all places
        places.add(a);
    }

    public void checkInLoc(Location userLoc) {
        for(Place place : places) {
            if( place.inPlace(userLoc) )
                break;
        }
    }
}
