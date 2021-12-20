package com.example.trybil.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.trybil.R;
import com.example.trybil.databinding.HomeFragmentBinding;
import com.example.trybil.viewmodel.MainViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeFragmentBinding;
    private NavController navController;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        mainViewModel.getPlaces().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                homeFragmentBinding.text1.setText(strings.get(0));
                homeFragmentBinding.text2.setText(strings.get(1));
                homeFragmentBinding.text3.setText(strings.get(2));
                homeFragmentBinding.text4.setText(strings.get(3));
                homeFragmentBinding.text5.setText(strings.get(4));
            }
        });

        mainViewModel.getPopulations().observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                homeFragmentBinding.count1.setText(integers.get(0).toString());
                homeFragmentBinding.count2.setText(integers.get(1).toString());
                homeFragmentBinding.count3.setText(integers.get(2).toString());
                homeFragmentBinding.count4.setText(integers.get(3).toString());
                homeFragmentBinding.count5.setText(integers.get(4).toString());

                homeFragmentBinding.simpleProgressBar1.setProgress(integers.get(0));
                homeFragmentBinding.simpleProgressBar2.setProgress(integers.get(1));
                homeFragmentBinding.simpleProgressBar3.setProgress(integers.get(2));
                homeFragmentBinding.simpleProgressBar4.setProgress(integers.get(3));
                homeFragmentBinding.simpleProgressBar5.setProgress(integers.get(4));
            }
        });

        mainViewModel.getLocation().observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> location) {
                //homeFragmentBinding.textDist1.setText("Longitude: " + location.get(0) + "size: " + location.size());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false);
        return homeFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        mainViewModel.getPopulations().observe(getViewLifecycleOwner(), new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                homeFragmentBinding.count1.setText(integers.get(0).toString());
                homeFragmentBinding.count2.setText(integers.get(1).toString());
                homeFragmentBinding.count3.setText(integers.get(2).toString());
                homeFragmentBinding.count4.setText(integers.get(3).toString());
                homeFragmentBinding.count5.setText(integers.get(4).toString());

                homeFragmentBinding.simpleProgressBar1.setProgress(integers.get(0));
                homeFragmentBinding.simpleProgressBar2.setProgress(integers.get(1));
                homeFragmentBinding.simpleProgressBar3.setProgress(integers.get(2));
                homeFragmentBinding.simpleProgressBar4.setProgress(integers.get(3));
                homeFragmentBinding.simpleProgressBar5.setProgress(integers.get(4));
            }
        });

        homeFragmentBinding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace("BCC Cafeteria");
            }
        });

        homeFragmentBinding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace("Break Cafe");
            }
        });

        homeFragmentBinding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace("Cafe In");
            }
        });

        homeFragmentBinding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace("Mozart Cafe");
            }
        });

        homeFragmentBinding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace("Speed Cafe");
            }
        });
    }

}