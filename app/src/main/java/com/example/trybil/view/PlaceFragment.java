package com.example.trybil.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trybil.databinding.PlaceFragmentBinding;
import com.example.trybil.model.Place;
import com.example.trybil.viewmodel.MainViewModel;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;
    private MainViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);


        mViewModel.getPlace().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                placeFragmentBinding.placeName.setText(place.getPlaceName());
                placeFragmentBinding.averageRate.setRating(2);
            }
        });

        mViewModel.getRating().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                placeFragmentBinding.ratingBar.setRating(integer);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        placeFragmentBinding = PlaceFragmentBinding.inflate(inflater, container, false);
        return placeFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeFragmentBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }
}