package com.example.trybil.model;

import static com.example.trybil.model.App.CHANNEL_ID1;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.trybil.R;
import com.example.trybil.view.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainRepository {
    private final Application application;
    private final FirebaseAuth auth;
    private final FirebaseStorage storage;
    private final MutableLiveData<User> user;
    private final MutableLiveData<User> searchUser;
    private final MutableLiveData<Boolean> isFriend;
    private final MutableLiveData<ArrayList<User>> userRequest;
    private final MutableLiveData<Bitmap> picture;
    private final MutableLiveData<Bitmap> searchPicture;
    private final MutableLiveData<ArrayList<Place>> places;
    private final MutableLiveData<ArrayList<Integer>> location;
    private final MutableLiveData<Integer> rating;
    private final MutableLiveData<ArrayList<String>> friends;
    private final ArrayList<String>  friendsUid;
    private final MutableLiveData<ArrayList<User>> inPlaceFriends;
    private final MutableLiveData<ArrayList<String>> requests;
    private final MutableLiveData<Place> place;
    private final MutableLiveData<Boolean> priv;
    private final DatabaseReference dbRef;
    private static MainRepository mainRepositorySingleton;
    private String searchedUid;
    private PlaceManager placeManager;

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
        storage = FirebaseStorage.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        user = new MutableLiveData<User>();
        searchUser = new MutableLiveData<User>();
        isFriend = new MutableLiveData<>();
        userRequest = new MutableLiveData<ArrayList<User>>();
        picture = new MutableLiveData<Bitmap>();
        searchPicture = new MutableLiveData<Bitmap>();
        places = new MutableLiveData<ArrayList<Place>>();
        location = new MutableLiveData<ArrayList<Integer>>();
        rating = new MutableLiveData<Integer>();
        friends = new MutableLiveData<ArrayList<String>>();
        friendsUid = new ArrayList<>();
        inPlaceFriends = new MutableLiveData<ArrayList<User>>();
        requests = new MutableLiveData<ArrayList<String>>();
        place = new MutableLiveData<>();
        priv = new MutableLiveData<>();
        //placeManager = new PlaceManager();

        pullPlaces();
        if(!auth.getCurrentUser().isAnonymous()) {
            pullUser();
            pullFriends();
            pullPic();
            pullLocations();
            pullPriv();
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
        Query query = dbRef.child("Places").orderByChild("peopleNumber");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Place> pulledPlaces = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledPlaces.add(ds.getValue(Place.class));
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

        /*
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

         */
    }

    private void pullFriends() {
        dbRef.child("Friends").child(auth.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> pulledFriends = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledFriends.add(ds.getValue().toString());
                    friendsUid.add(ds.getKey());
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
                ArrayList<String> uids = new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()) {
                    pulledRequests.add(ds.getValue().toString());
                    uids.add(ds.getKey());
                }

                requests.postValue(pulledRequests);
                reqUserArray(uids);
                Toast.makeText(application, "SUCCESS_Requests: ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Requests: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reqUserArray(ArrayList<String> uids) {
        ArrayList<User> usersRequest = new ArrayList<>();

        if (uids.size() == 0) {
            userRequest.postValue(usersRequest);
        }

        for(String uid : uids) {
            dbRef.child("Users").child(uid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    usersRequest.add(dataSnapshot.getValue(User.class));

                    if (usersRequest.size() == uids.size()) {
                        userRequest.postValue(usersRequest);
                        notifyUser(usersRequest);
                    }
                }
            });
        }
    }

    public void searchUser(String username) {
        dbRef.child("Usernames").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    dbRef.child("Users").child(snapshot.getValue(String.class)).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Toast.makeText(application, "SEARCH PULLED: " + dataSnapshot.getValue(User.class).getUsername(), Toast.LENGTH_SHORT).show();
                            searchUser.postValue(dataSnapshot.getValue(User.class));
                            pullSearchPic(dataSnapshot.getKey());
                            searchedUid = (dataSnapshot.getKey());
                            isFriend();
                        }
                    });
                }
                catch (NullPointerException exception) {
                    Toast.makeText(application, "User Not Exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Search: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadPic(Uri image) {
        storage.getReference("images/" + auth.getUid()).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(application, "IMAGE UPLOADED", Toast.LENGTH_SHORT).show();
                pullPic();
            }
        });
    }

    public void changePriv(boolean priv) {
        if (priv)
            dbRef.child("Users").child(auth.getUid()).child("priv").setValue(true);
        else
            dbRef.child("Users").child(auth.getUid()).child("priv").setValue(false);
    }

    public void pullPic() {
        try {
            final File tmpFile = File.createTempFile(auth.getUid(), "jpg");

            storage.getReference("images/" + auth.getUid()).getFile(tmpFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(application, "IMAGE PULLED", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
                    picture.postValue(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    picture.postValue(null);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pullSearchPic(String uid) {
        try {
            final File tmpFile = File.createTempFile(uid, "jpg");

            storage.getReference("images/" + uid).getFile(tmpFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(application, "IMAGE PULLED", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
                    searchPicture.postValue(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    searchPicture.postValue(null);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriend() {
        dbRef.child("Friends").child(searchedUid).child("requests").child(auth.getUid()).setValue(user.getValue().getUsername());
        Toast.makeText(application, "FRIEND REQUEST SENT", Toast.LENGTH_SHORT).show();
    }

    public void removeFriend() {
        dbRef.child("Friends").child(searchedUid).child("friends").child(auth.getUid()).removeValue();
        dbRef.child("Friends").child(auth.getUid()).child("friends").child(searchedUid).removeValue();
        Toast.makeText(application, "friend is removed", Toast.LENGTH_SHORT).show();
    }

    public void acceptReq(String username) {
        dbRef.child("Usernames").child(username).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getValue(String.class);
                dbRef.child("Friends").child(auth.getUid()).child("friends").child(uid).setValue(username);
                dbRef.child("Friends").child(uid).child("friends").child(auth.getUid()).setValue(user.getValue().getUsername());
                dbRef.child("Friends").child(auth.getUid()).child("requests").child(uid).removeValue();
            }
        });
    }

    public void rejectReq(String username) {
        dbRef.child("Usernames").child(username).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getValue(String.class);

                dbRef.child("Friends").child(auth.getUid()).child("requests").child(uid).removeValue();
            }
        });
    }

    public void isFriend() {
        dbRef.child("Friends").child(auth.getUid()).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(searchedUid))
                        found = true;
                }

                if (found) {
                    isFriend.postValue(true);
                }
                else {
                    isFriend.postValue(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void changePlace(String name) {
        dbRef.child("Places").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                place.postValue(snapshot.getValue(Place.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbRef.child("Ratings").child(auth.getUid()).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    rating.postValue(snapshot.getValue(Integer.class));
                }
                else {
                    rating.postValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "ERROR_Rating: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRating(Integer rating) {
        if (rating != 0)
            dbRef.child("Ratings").child(auth.getUid()).child(place.getValue().getPlaceName()).setValue(rating);
    }

    public void pullInPlace() {
        HashMap<String, String> hashMap;

        if (place.getValue().getUserInLocation() != null)
            hashMap = place.getValue().getUserInLocation();
        else
            hashMap = new HashMap<>();

        ArrayList<String> friendsIn = new ArrayList<>();
        ArrayList<User> friendsInUser = new ArrayList<>();

        for(String friend: friendsUid) {
            if (hashMap.get(friend) != null) {
                friendsIn.add(hashMap.get(friend));
            }
        }


        dbRef.child("Users").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User tmpUser = ds.getValue(User.class);
                    if (!tmpUser.getPriv()) {
                        for (String friend : friendsIn) {
                            if (friend.equals(ds.getKey())) {
                                friendsInUser.add(ds.getValue(User.class));
                            }
                        }
                    }
                }
                inPlaceFriends.postValue(friendsInUser);
            }
        });
    }

    public void pullPriv() {
        dbRef.child("Users").child(auth.getUid()).child("priv").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                priv.postValue(snapshot.getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "ERROR_Private: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notifyUser(ArrayList<User> userRequest) {
        String text = userRequest.size() > 1 ? "friend requests" : "friend request";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                application.getApplicationContext(), CHANNEL_ID1)
                .setSmallIcon(R.drawable.bilgit_logo)
                .setContentTitle("BilGit")
                .setContentText("You have " + userRequest.size() + " " + text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = new NavDeepLinkBuilder(application.getApplicationContext())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_main)
                .setDestination(R.id.profileFragment)
                .createPendingIntent();

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) application.getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

    public MutableLiveData<User> getUser() { return user; }

    public MutableLiveData<User> getSearchUser() { return searchUser; }

    public MutableLiveData<Boolean> getIsFriend() {
        return isFriend;
    }

    public MutableLiveData<ArrayList<User>> getInPlaceFriends() {
        return inPlaceFriends;
    }

    public MutableLiveData<ArrayList<User>> getUserRequest() { return userRequest; }

    public MutableLiveData<Bitmap> getPicture() { return picture; }

    public MutableLiveData<Bitmap> getSearchPicture() { return searchPicture; }

    public MutableLiveData<ArrayList<Place>> getPlaces() { return places; }

    public MutableLiveData<Place> getPlace() { return place; }

    public MutableLiveData<ArrayList<Integer>> getLocation() { return location; }

    public MutableLiveData<ArrayList<String>> getFriends() { return friends; }

    public MutableLiveData<ArrayList<String>> getRequests() { return requests; }

    public MutableLiveData<Integer> getRating() { return rating; }

    public MutableLiveData<Boolean> getPriv() {
        return priv;
    }
    //public PlaceManager getPlaceManager() { return placeManager; }
}
