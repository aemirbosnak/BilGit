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

import com.example.trybil.R;
import com.example.trybil.databinding.HomeFragmentBinding;
import com.example.trybil.model.Place;
import com.example.trybil.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeFragmentBinding;
    private NavController navController;
    private MainViewModel mainViewModel;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                int newNumber = dataSnapshot.getValue(int.class);
                // ..
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("this", "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabase.child("Places").child("BCC").child("peopleNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeFragmentBinding.simpleProgressBar1.setProgress(
                mainViewModel.getPlace().getValue(snapshot.getValue(Place.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mainViewModel.getPlace().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                homeFragmentBinding.simpleProgressBar1.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar2.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar3.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar4.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar5.setProgress(place.getPeopleNumber());
            }
        });

        mainViewModel.getPlaces().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                homeFragmentBinding.text1.setText(strings.get(0));
                homeFragmentBinding.text2.setText(strings.get(1));
                homeFragmentBinding.text3.setText(strings.get(2));
                homeFragmentBinding.text4.setText(strings.get(3));
                homeFragmentBinding.text5.setText(strings.get(4));

                /*
                homeFragmentBinding.simpleProgressBar1.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar2.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar3.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar4.setProgress(place.getPeopleNumber());
                homeFragmentBinding.simpleProgressBar5.setProgress(place.getPeopleNumber());
                 */
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