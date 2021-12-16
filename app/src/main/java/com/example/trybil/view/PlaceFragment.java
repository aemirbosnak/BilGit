package com.example.trybil.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trybil.databinding.PlaceFragmentBinding;
import com.example.trybil.databinding.ProfileFragmentBinding;
import com.example.trybil.viewmodel.MainViewModel;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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