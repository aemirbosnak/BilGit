package com.example.trybil.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trybil.databinding.PlaceFragmentBinding;
import com.example.trybil.model.InPlaceAdapter;
import com.example.trybil.model.Place;
import com.example.trybil.model.User;
import com.example.trybil.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;


public class PlaceFragment extends Fragment {
    private PlaceFragmentBinding placeFragmentBinding;
    private MainViewModel mViewModel;
    private DatabaseReference databaseReference;
    private InPlaceAdapter inPlaceAdapter;
    private Integer oldRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mViewModel.getRating().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                placeFragmentBinding.ratingBar.setRating(integer);
                oldRating = integer;
            }
        });

        mViewModel.getPlace().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                mViewModel.pullInPlace();
                placeFragmentBinding.placeName.setText(place.getPlaceName());
                String initialRate = String.format("%.2g%n", place.getTotalRating() / (float)(place.getVoteNumber()));
                if(Float.parseFloat(initialRate) > 0) {
                    placeFragmentBinding.averageRateText.setText(initialRate);
                    placeFragmentBinding.averageRate.setRating(Float.parseFloat(initialRate));
                }
                else{
                    placeFragmentBinding.averageRateText.setText("No rate");
                    placeFragmentBinding.averageRate.setRating(0);
                }
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
            int currentRate = (int) placeFragmentBinding.ratingBar.getRating();

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        inPlaceAdapter = new InPlaceAdapter(getContext(), getActivity().getApplication(), new ArrayList<User>());
        placeFragmentBinding.recylclerInPlace.setAdapter(inPlaceAdapter);
        placeFragmentBinding.recylclerInPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel.getInPlaceFriends().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                inPlaceAdapter.setFriends(users);
                inPlaceAdapter.notifyDataSetChanged();
            }
        });

        placeFragmentBinding.btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) placeFragmentBinding.ratingBar.getRating();
                Place place = mViewModel.getPlace().getValue();
                if(oldRating != null) {
                    databaseReference.child("Places").child(place.getPlaceName()).child("voteNumber")
                            .runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                    Integer vote = currentData.getValue(Integer.class);

                                    currentData.setValue(vote - 1);
                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError error, boolean committed,
                                                       @Nullable DataSnapshot currentData) {}
                            });
                }

                final float[] newRate = new float[1];
                final float[] newVoteNumber = new float[1];
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
                                    currentData.setValue(totalRating - oldRating + rating);
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
                            public void onComplete(@Nullable DatabaseError error, boolean committed,
                                                   @Nullable DataSnapshot currentData) {}
                        });

                String newText = String.format("%.2g%n", place.getTotalRating() / (float)(place.getVoteNumber()));
                placeFragmentBinding.ratingBar.setRating(rating);
                placeFragmentBinding.averageRate.setRating(Float.parseFloat(newText));
                placeFragmentBinding.averageRateText.setText(newText);

                mViewModel.setRating((int) rating);
            }
        });
    }
}