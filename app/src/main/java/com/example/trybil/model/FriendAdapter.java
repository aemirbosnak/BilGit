package com.example.trybil.model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trybil.R;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    ArrayList<User> users;
    //ArrayList<Bitmap> images;
    Context context;
    MainRepository repository;
    private FriendAdapter adapter;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtUsernameCard.setText(users.get(position).getUsername());
        holder.txtDepartmentCard.setText(users.get(position).getDepartment());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.addFriend();
            }
        });
        //holder.btnReject.setOnClickListener();
        //holder.imgCard.setImageBitmap(images.get(position));
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
        ImageView imgCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsernameCard = itemView.findViewById(R.id.txtUsernameCard);
            txtDepartmentCard = itemView.findViewById(R.id.txtDepartmentCard);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
            imgCard = itemView.findViewById(R.id.imgCard);
        }
    }
}