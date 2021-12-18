package com.example.trybil.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "locationServiceChannel";
    public static final String CHANNEL_ID1 = "Friend Request Notification Channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        createNotificationChannel1();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel locationChannel = new NotificationChannel( CHANNEL_ID,
                    "Location Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            locationChannel.setSound(null, null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(locationChannel);
        }
    }

    private void createNotificationChannel1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID1, "Friend Request Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is notification channel for friend requests");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
