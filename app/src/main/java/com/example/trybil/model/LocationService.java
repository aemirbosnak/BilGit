package com.example.trybil.model;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.Toast;
import android.os.Process;

import androidx.annotation.Nullable;

public class LocationService extends Service {
    UserLocation userLocation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        userLocation = new UserLocation( (LocationManager) getSystemService(LOCATION_SERVICE),
                this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        //method not ready
        //interactions fot starting and stopping the service
        //userLocation.setLocation();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service stopping", Toast.LENGTH_SHORT).show();
    }
}
