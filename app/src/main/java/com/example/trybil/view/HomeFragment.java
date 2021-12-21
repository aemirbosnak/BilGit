package com.example.trybil.view;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trybil.R;
import com.example.trybil.databinding.HomeFragmentBinding;
import com.example.trybil.model.Place;
import com.example.trybil.model.PlaceAdapter;
import com.example.trybil.viewmodel.MainViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeFragmentBinding;
    private NavController navController;
    private MainViewModel mainViewModel;
    private PlaceAdapter placeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
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
        ArrayList<Place> tmpPlaces = new ArrayList<>();
        placeAdapter = new PlaceAdapter(getContext(), getActivity().getApplication(), tmpPlaces, mainViewModel, navController);
        homeFragmentBinding.recylclerPlace.setAdapter(placeAdapter);
        homeFragmentBinding.recylclerPlace.setLayoutManager(new LinearLayoutManager(getContext()));

        mainViewModel.getPlaces().observe(getViewLifecycleOwner(), new Observer<ArrayList<Place>>() {
            @Override
            public void onChanged(ArrayList<Place> places) {
                placeAdapter.setPlaces(places);
                placeAdapter.notifyDataSetChanged();
            }
        });

    }
}