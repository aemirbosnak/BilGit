package com.example.trybil.model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trybil.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    ArrayList<User> users;
    Context context;
    MainRepository repository;

    public FriendAdapter(Context context, Application application, ArrayList<User> friends) {
        this.context = context;
        this.users = friends;
        repository = MainRepository.getInstance(application);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.friend_card, parent, false);
        return new MyViewHolder(view);
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtUsernameCard.setText(users.get(position).getUsername());
        holder.txtDepartmentCard.setText(users.get(position).getDepartment());

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.acceptReq(users.get(position).getUsername());
            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.rejectReq(users.get(position).getUsername());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsernameCard;
        TextView txtDepartmentCard;
        Button btnAccept;
        Button btnReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsernameCard = itemView.findViewById(R.id.cardUsername);
            txtDepartmentCard = itemView.findViewById(R.id.txtDepartmentCard);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
