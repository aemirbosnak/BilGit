package com.example.trybil.model;

import android.app.Notification;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.trybil.R;
import com.example.trybil.view.MainActivity;
import com.google.firebase.FirebaseApp;


public class LocationService extends Service {
    UserLocation userLocation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(getApplicationContext());
        userLocation = new UserLocation( (LocationManager) getSystemService(LOCATION_SERVICE),
                this);
        Toast.makeText(this, "service starting ",
                Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new NotificationCompat.Builder(this, App.CHANNEL_ID)
                        .setContentTitle("Bilgit")
                        .setContentText("Location is open")
                        .setSmallIcon(R.drawable.bilgit_logo)
                        .setContentIntent(pendingIntent)
                        .setSilent(true)
                        .build();

        startForeground(1, notification);


        userLocation.updateLocation();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service stopping", Toast.LENGTH_SHORT).show();
    }
}
