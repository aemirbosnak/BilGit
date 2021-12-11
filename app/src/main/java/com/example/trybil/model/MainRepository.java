package com.example.trybil.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainRepository {
    private final Application application;
    private final FirebaseAuth auth;
    private final MutableLiveData<User> user;
    private final DatabaseReference dbRef;
    private static MainRepository mainRepositorySingleton;

    public static MainRepository getInstance(Application application) {
        if(mainRepositorySingleton != null) {
            return mainRepositorySingleton;
        }
        else {
            return new MainRepository(application);
        }
    }

    private MainRepository(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        user = new MutableLiveData<User>();
        pullUser();
    }

    public void pullUser() {
        dbRef.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(application, "USER PULLED: " + snapshot.getValue(User.class).getUsername(), Toast.LENGTH_SHORT).show();
                user.postValue(snapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}