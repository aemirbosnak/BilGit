package com.example.trybil.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserLocation {

    private DatabaseReference mDatabase;

    LocationManager lm;
    Context mContext;

    //Criteria for best location provider
    Criteria criteria;
    String best;

    PlaceManager placeManager;
    boolean inPlace;

    Location userLoc;
    double longitude;
    double latitude;

    /**
     * @param l initialized LocationManger object passed from MainActivity
     * @param c Context object from MainActivity
     */
    public UserLocation(LocationManager l, Context c)
    {
        placeManager = new PlaceManager();
        inPlace = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lm = l;
        mContext = c;
        criteria = new Criteria();
        best = lm.getBestProvider(criteria, true);
        userLoc = new Location(best);
    }

    /*
        Look at all the places in the database
        If this location is inside some place
        put ID of this device's user into that place as ID data
        number of user IDs inside the place class is number of
        people present at that place
     */
    public void compareLocationPlace()
    {
        if(!inPlace)
            placeManager.checkInLoc(userLoc);
    }

    public void setLocation()
    {
        //Check permission
        if ( checkPermission() ) {
            //When permission granted
            //Initialize Location object
            if( locationEnabled() )
            {
                if( userLoc == null )
                {
                    updateLocation();
                }
                else
                {
                    userLoc = lm.getLastKnownLocation(best);
                }
            }
        }
        else
        {
            requestPermissions();
        }
    }

    private void updateLocation()
    {
        //Check permission
        if ( checkPermission() )
        {
            //When permission granted
            //Request location updates with the given time and distance intervals
            lm.requestLocationUpdates(best,2000, 10, locationListener);
        }
    }

    private boolean checkPermission()
    {
        return ( ActivityCompat.checkSelfPermission( mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED )
                || ( ActivityCompat.checkSelfPermission( mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED );
    }

    private void requestPermissions()
    {
        ActivityCompat.requestPermissions( (Activity)mContext, new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 44 );
    }

    private boolean locationEnabled()
    {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location)
        {
            setLocation();
            compareLocationPlace();
        }
    };

    //Getters setter for long lat
    public double getLongitude()
    {
        longitude = userLoc.getLongitude();
        return longitude;
    }
    public double getLatitude()
    {
        latitude = userLoc.getLatitude();
        return latitude;
    }
    public Location getUserLoc() {
        return userLoc;
    }
}
