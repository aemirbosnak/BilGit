package com.example.trybil.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.databinding.HomeFragmentBinding;
import com.example.trybil.model.User;
import com.example.trybil.R;
import com.example.trybil.viewmodel.MainViewModel;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeFragmentBinding;
    private NavController navController;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getUser().observe(this, new Observer<User>() {

            @Override
            public void onChanged(User user) {
                homeFragmentBinding.titleView.setText("Welcome user: " + user.getUsername());
                mainViewModel.getUser().removeObservers(getViewLifecycleOwner());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false);
        return homeFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        homeFragmentBinding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
            }
        });

        homeFragmentBinding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
            }
        });

        homeFragmentBinding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
            }
        });

        homeFragmentBinding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
            }
        });

        homeFragmentBinding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
            }
        });
    }

}