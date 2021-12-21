package com.example.trybil.model;

import android.location.Location;

import java.util.ArrayList;

public class PlaceManager {
    static ArrayList<Place> places = new ArrayList<>();

    Place mozartCafe = new Place( "Mozart Cafe", 0.0003, 32.7481, 39.8687);
    Place breakCafe = new Place( "Break Cafe", 0.0003, 32.748889,39.868056);
    Place speedCafe = new Place( "Speed Cafe", 0.0003, 32.7483, 39.8663);
    Place cafeIn = new Place("Cafe In", 0.0003, 32.7506, 39.8700);
    Place bbcCafeteria = new Place("BCC Cafeteria", 0.0003, 32.7461, 39.8743);

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
