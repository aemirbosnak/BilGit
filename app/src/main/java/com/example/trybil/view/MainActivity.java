package com.example.trybil.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trybil.R;
import com.example.trybil.databinding.ActivityMainBinding;
import com.example.trybil.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    NavHostFragment navHostFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        /*
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
         */

        // Classes could be passed by implementing Serializable on passed class then cast it into that class
        //String name = getIntent().getStringExtra("EXTRA");
        //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();

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
                        navController.navigate(R.id.settingsFragment);
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