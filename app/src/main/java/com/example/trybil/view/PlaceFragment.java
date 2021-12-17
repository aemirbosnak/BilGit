package com.example.trybil.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.trybil.R;
import com.example.trybil.databinding.PlaceFragmentBinding;
import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.viewmodel.MainViewModel;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;
    Button button;
    RatingBar ratingStars;
    int myRating = 0;
    //I'm not sure about these values
    int totalRate = 0;
    int rateNum = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        button = button.findViewById(); //I'm not sure whether this should be "button"
        ratingStars = ratingStars.findViewById();

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {

                int rating = (int) v;
                String message = "1.0f / 5";
                myRating = (int) ratingBar.getRating();
                totalRate = totalRate + myRating;
                rateNum++;
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                ratingBar.setRating(totalRate / rateNum);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, String.valueOf(myRating), Toast.LENGTH_SHORT).show();
            }
        });*/
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

        /*
        placeFragmentBinding.ratingBar.setOnRatingBarChangeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
        /*
        private ProfileFragmentBinding profileFragmentBinding;
        private MainViewModel mainViewModel;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false);
            return profileFragmentBinding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

         */
    }


}