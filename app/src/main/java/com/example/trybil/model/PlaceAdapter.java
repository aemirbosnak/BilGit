package com.example.trybil.model;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trybil.R;
import com.example.trybil.viewmodel.MainViewModel;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{
    ArrayList<Place> places;
    Context context;
    MainRepository repository;
    MainViewModel mainViewModel;
    NavController navController;
    private PlaceAdapter adapter;

    public PlaceAdapter(Context context, Application application, ArrayList<Place> places, MainViewModel viewModel, NavController navController) {
        this.context = context;
        this.places = places;
        this.mainViewModel = viewModel;
        this.navController = navController;
        repository = MainRepository.getInstance(application);
    }

    @NonNull
    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_place, parent, false);
        return new PlaceAdapter.PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int position) {
        holder.nameOfPlace.setText(places.get(position).getPlaceName());
        holder.count.setText(String.valueOf(places.get(position).getPeopleNumber()));
        holder.progBarDensity.setProgress(places.get(position).getPeopleNumber());
        holder.cardPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.placeFragment);
                mainViewModel.changePlace(places.get(position).getPlaceName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        CardView cardPlace;
        TextView nameOfPlace;
        ProgressBar progBarDensity;
        TextView count;


        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPlace = itemView.findViewById(R.id.cardPlace);
            nameOfPlace = itemView.findViewById(R.id.nameOfPlace);
            count = itemView.findViewById(R.id.count);
            progBarDensity = itemView.findViewById(R.id.progBarDensity);
        }
    }
}
