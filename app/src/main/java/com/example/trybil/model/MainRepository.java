package com.example.trybil.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainRepository {
    private final Application application;
    private final FirebaseAuth auth;
    private final MutableLiveData<User> user;
    private final MutableLiveData<User> searchUser;
    private final MutableLiveData<ArrayList<String>> places;
    private final MutableLiveData<ArrayList<Integer>> location;
    private final MutableLiveData<ArrayList<String>> friends;
    private final MutableLiveData<ArrayList<String>> requests;
    private final DatabaseReference dbRef;
    private static MainRepository mainRepositorySingleton;

    public static MainRepository getInstance(Application application) {
        if(mainRepositorySingleton != null) {
            return mainRepositorySingleton;
        }
        else {
            return new MainRepository(application);
        }
    }

    private MainRepository(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        user = new MutableLiveData<User>();
        searchUser = new MutableLiveData<User>();
        places = new MutableLiveData<ArrayList<String>>();
        location = new MutableLiveData<ArrayList<Integer>>();
        friends = new MutableLiveData<ArrayList<String>>();
        requests = new MutableLiveData<ArrayList<String>>();
        pullPlaces();
        if(!auth.getCurrentUser().getEmail().isEmpty()) {
            pullUser();
            pullFriends();
            //pullLocations();
        }
    }

    private void pullUser() {
            dbRef.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Toast.makeText(application, "USER PULLED: " + snapshot.getValue(User.class).getUsername(), Toast.LENGTH_SHORT).show();
                    user.postValue(snapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(application, "Error_Users: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void pullPlaces() {
        dbRef.child("Places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> pulledPlaces = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledPlaces.add(ds.child("Name").getValue().toString());
                }

                places.postValue(pulledPlaces);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Places: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pullLocations() {
        /*
        dbRef.child("Locations").child(auth.getUid()).child("longitude").setValue(1111);
        dbRef.child("Locations").child(auth.getUid()).child("latitude").setValue(2222);
         */

        dbRef.child("Locations").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Integer> pulledLocation = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledLocation.add(Integer.valueOf(ds.getValue().toString()));
                }

                location.postValue(pulledLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Places: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pullFriends() {
        //dbRef.child("Friends").child(auth.getUid()).child("friends").child("5alEHG8cYQaml79o8gzMbGGD0aq2").setValue("5alEHG8cYQaml79o8gzMbGGD0aq2");
        //dbRef.child("Friends").child(auth.getUid()).child("requests").child("YSojbv7dA1cDcP404uixMdwDeSq2").setValue("YSojbv7dA1cDcP404uixMdwDeSq2");

        dbRef.child("Friends").child(auth.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> pulledFriends = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledFriends.add(ds.getValue().toString());
                }

                friends.postValue(pulledFriends);
                Toast.makeText(application, "SUCCESS_Friends: ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Friends: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dbRef.child("Friends").child(auth.getUid()).child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> pulledRequests = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledRequests.add(ds.getValue().toString());
                }

                requests.postValue(pulledRequests);
                Toast.makeText(application, "SUCCESS_Requests: ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Requests: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchUser(String username) {
        dbRef.child("Usernames").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbRef.child("Users").child(snapshot.getValue(String.class)).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Toast.makeText(application, "SEARCH PULLED: " + dataSnapshot.getValue(User.class).getUsername(), Toast.LENGTH_SHORT).show();
                        searchUser.postValue(dataSnapshot.getValue(User.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Search: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<User> getSearchUser() {
        return searchUser;
    }

    public MutableLiveData<ArrayList<String>> getPlaces() {
        return places;
    }

    public MutableLiveData<ArrayList<Integer>> getLocation() {
        return location;
    }

    public MutableLiveData<ArrayList<String>> getFriends() {
        return friends;
    }

    public MutableLiveData<ArrayList<String>> getRequests() {
        return requests;
    }
}
