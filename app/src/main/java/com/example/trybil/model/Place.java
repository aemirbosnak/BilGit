package com.example.trybil.model;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class Place {

    private String placeName;
    private double radius;
    private double longitude;
    private double latitude;
    private float rating;

    //for test use
    public Place(String placeName, double radius, double longitude, double latitude) {
        this.placeName = placeName;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getRating(){
        return rating;
    }

    public void setRating(float newRate){
        rating = newRate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public double getRadius() {
        return radius;
    }

    public Place inPlaceCheck( Location userLoc ) {
        if( (latitude - userLoc.getLatitude()) * (latitude - userLoc.getLatitude())
                + (longitude - userLoc.getLongitude()) * (longitude - userLoc.getLongitude())
                <= radius * radius ) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Places")
                    .child(placeName);

            //transaction to prevent data loss in simultaneous incrementation
            ref.child("People number").runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Integer current = currentData.getValue(Integer.class);
                    if(current == null) {
                        currentData.setValue(1);
                    }
                    else {
                        currentData.setValue(current + 1);
                    }

                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed,
                                       @Nullable DataSnapshot currentData) {
                }
            });

           ref.child("Users in location").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                   .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

           FirebaseDatabase.getInstance().getReference().child("Locations")
                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Place")
                   .setValue(placeName);

           return this;
        }

        return null;
    }


    public static boolean outPlaceCheck( Location userLoc, Place place ) {
        if( (place.latitude - userLoc.getLatitude()) * (place.latitude - userLoc.getLatitude())
                + (place.longitude - userLoc.getLongitude()) * (place.longitude - userLoc.getLongitude())
                > place.radius * place.radius ) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Places")
                    .child(place.placeName);

            //transaction to prevent data loss in simultaneous incrementation
            ref.child("People number").runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Integer current = currentData.getValue(Integer.class);
                    if(current == null || current == 0) {
                        currentData.setValue(0);
                    }
                    else {
                        currentData.setValue(current - 1);
                    }

                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed,
                                       @Nullable DataSnapshot currentData) {
                }
            });


            ref.child("Users in location").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .removeValue();

            FirebaseDatabase.getInstance().getReference().child("Locations")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Place")
                    .setValue("none");

            return true;
        }

        return false;
    }

}
