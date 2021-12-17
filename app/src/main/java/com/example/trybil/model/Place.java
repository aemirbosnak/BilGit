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
    private double rating;

    //for test use
    public Place(String placeName, double radius, double longitude, double latitude) {
        this.placeName = placeName;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public boolean inPlace( Location userLoc ) {
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

           ref.child("Users in location").push().setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

           FirebaseDatabase.getInstance().getReference().child("Users")
                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("InPlace")
                   .setValue(true);

           return true;
        }

        return false;
    }
}