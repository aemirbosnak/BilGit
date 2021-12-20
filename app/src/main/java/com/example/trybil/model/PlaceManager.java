package com.example.trybil.model;

import android.location.Location;

import java.util.ArrayList;

public class PlaceManager {
    static ArrayList<Place> places = new ArrayList<>();

    Place mozartCafe = new Place( "Mozart Cafe", 15, 32.747971, 39.868728);
    Place breakCafe = new Place( "Break Cafe", 5, 32.748889,39.868056);
    Place speedCafe = new Place( "Speed Cafe", 10, 32.748333, 39.865833);
    Place cafeIn = new Place("Cafe In", 5, 32.750278, 39.869722);
    Place bbcCafeteria = new Place("BCC Cafeteria", 30, 32.750278, 39.870556);

    public PlaceManager() {
        places.add(mozartCafe);
        places.add(breakCafe);
        places.add(speedCafe);
        places.add(cafeIn);
        places.add(bbcCafeteria);
    }

    public Place checkInLoc(Location userLoc) {
        for(Place place : places) {
            if( place.inPlaceCheck(userLoc) != null )
                return place;
        }
        return null;
    }

    public int numPlaces() {
        int x = 0;
        for( Place p : places )
            x++;
        return x;
    }

    public Place getPlace(int index) {
        return places.get(index);
    }
}
