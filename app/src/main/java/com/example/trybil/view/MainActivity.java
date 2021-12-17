package com.example.trybil.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trybil.R;
import com.example.trybil.databinding.ActivityMainBinding;
import com.example.trybil.model.LocationService;
import com.example.trybil.model.UserLocation;
import com.example.trybil.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    NavHostFragment navHostFragment;
    NavController navController;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread(){
            @Override
            public void run(){
                Intent intent = new Intent(getApplicationContext(), LocationService.class);
                startService(intent);
            }
        };
        thread.start();

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerMain);
        navController = navHostFragment.getNavController();

        activityMainBinding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.menuItemHome:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.menuItemSearch:
                        navController.navigate(R.id.searchFragment);
                        break;
                    case R.id.menuItemProfile:
                        navController.navigate(R.id.profileFragment);
                        break;
                    case R.id.menuItemSettings:
                        navController.navigate(R.id.settingsActivity);
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        navController.navigate(R.id.homeFragment);
        activityMainBinding.bottomNavigation.setSelectedItemId(R.id.menuItemHome);
    }
}