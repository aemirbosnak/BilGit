package com.example.trybil.model;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trybil.R;

import java.util.ArrayList;

public class InPlaceAdapter extends RecyclerView.Adapter<InPlaceAdapter.SecViewHolder>{
    ArrayList<User> users;
    Context context;
    MainRepository repository;
    private InPlaceAdapter adapter;

    public InPlaceAdapter(Context context, Application application, ArrayList<User> friends) {
        this.context = context;
        this.users = friends;
        repository = MainRepository.getInstance(application);
    }

    @NonNull
    @Override
    public SecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.friend_of_user_card, parent, false);
        return new SecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecViewHolder holder, int position) {
        holder.cardUsername.setText(users.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class SecViewHolder extends RecyclerView.ViewHolder {
        TextView cardUsername;


        public SecViewHolder(@NonNull View itemView) {
            super(itemView);
            cardUsername = itemView.findViewById(R.id.cardUsername);
        }
    }
}
