package com.example.trybil.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.trybil.databinding.HomeFragmentBinding;
import com.example.trybil.databinding.PlaceFragmentBinding;
import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.viewmodel.MainViewModel;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;
    private MainViewModel mViewModel;
    CardView cardView;
    HomeFragmentBinding homeFragmentBinding;
    RatingBar ratingBar;
    RatingBar averageRate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        ratingBar = placeFragmentBinding.ratingBar;
        averageRate = placeFragmentBinding.averageRate;

        cardView = homeFragmentBinding.cardView1;

        mViewModel.getPlace().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                placeFragmentBinding.placeName.setText(s);
                placeFragmentBinding.averageRate.setRating();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float newRate = Float.parseFloat(mViewModel.getPlace().getValue()) + rating;
                mViewModel.getPlace().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {

                        placeFragmentBinding.placeName.setText(s);
                    }
                });
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
    }
}