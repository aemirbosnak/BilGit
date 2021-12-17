package com.example.trybil.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trybil.databinding.ActivityAuthBinding;
import com.example.trybil.model.LocationService;
import com.example.trybil.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    //AuthViewModel authViewModel;
    ActivityAuthBinding activityAuthBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAuthBinding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(activityAuthBinding.getRoot());
        //authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}