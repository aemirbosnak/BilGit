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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;
    private MainViewModel mViewModel;
    private DatabaseReference databaseReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        mViewModel.getPlace().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                placeFragmentBinding.placeName.setText(place.getPlaceName());
                placeFragmentBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        databaseReference.child("Places").child(place.getPlaceName()).child("totalRating")
                                .runTransaction(new Transaction.Handler() {
                                    @NonNull
                                    @Override
                                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                        Integer totalRating = currentData.getValue(Integer.class);
                                        if(totalRating == null){
                                            currentData.setValue(rating);
                                        }
                                        else{
                                            currentData.setValue(totalRating + rating);
                                        }
                                        return Transaction.success(currentData);
                                    }

                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                    }
                                });

                        databaseReference.child("Places").child(place.getPlaceName()).child("voteNumber")
                                .runTransaction(new Transaction.Handler() {
                                    @NonNull
                                    @Override
                                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                        Integer vote = currentData.getValue(Integer.class);
                                        if(vote == null){
                                            currentData.setValue(1);
                                        }
                                        else{
                                            currentData.setValue(vote + 1);
                                        }
                                        return Transaction.success(currentData);
                                    }

                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                    }
                                });

                        databaseReference.child("Ratings").child(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid()).child(place.getPlaceName()).setValue(rating);

                        placeFragmentBinding.ratingBar.setRating(3);
                        placeFragmentBinding.averageRate.setRating(1.1f);
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

        placeFragmentBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }
}