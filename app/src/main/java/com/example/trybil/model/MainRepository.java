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

import com.example.trybil.R;
import com.example.trybil.view.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainRepository {
    private final Application application;
    private final FirebaseAuth auth;
    private final FirebaseStorage storage;
    private final MutableLiveData<User> user;
    private final MutableLiveData<User> searchUser;
    private final MutableLiveData<ArrayList<User>> userRequest;
    private final MutableLiveData<Bitmap> picture;
    private final MutableLiveData<Bitmap> searchPicture;
    private final MutableLiveData<ArrayList<String>> places;
    private final MutableLiveData<ArrayList<Integer>> location;
    private final MutableLiveData<ArrayList<String>> friends;
    private final MutableLiveData<ArrayList<String>> requests;
    private final MutableLiveData<String> place;
    private final DatabaseReference dbRef;
    private static MainRepository mainRepositorySingleton;
    private String searchedUid;
    private boolean isFriend;

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
        userRequest = new MutableLiveData<ArrayList<User>>();
        picture = new MutableLiveData<Bitmap>();
        searchPicture = new MutableLiveData<Bitmap>();
        places = new MutableLiveData<ArrayList<String>>();
        location = new MutableLiveData<ArrayList<Integer>>();
        friends = new MutableLiveData<ArrayList<String>>();
        requests = new MutableLiveData<ArrayList<String>>();
        place = new MutableLiveData<>();

        pullPlaces();
        if(!auth.getCurrentUser().getEmail().isEmpty()) {
            pullUser();
            pullFriends();
            pullPic();
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
                    pulledPlaces.add(ds.child("placeName").getValue().toString());
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
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriend() {
        dbRef.child("Friends").child(searchedUid).child("requests").child(auth.getUid()).setValue(user.getValue().getUsername());
        Toast.makeText(application, "FRIEND REQUEST SENT", Toast.LENGTH_SHORT).show();
    }

    public void acceptReq(String username) {
        dbRef.child("Usernames").child(username).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getValue(String.class);
                dbRef.child("Friends").child(auth.getUid()).child("friends").child(uid).setValue(username);
                dbRef.child("Friends").child(uid).child("friends").child(auth.getUid()).setValue(auth.getUid());
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

    public boolean isFriend(String username) {
        dbRef.child("Friends").child(auth.getUid()).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren() ) {
                    if( ds.getValue(String.class).equals(username) )
                        isFriend = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return isFriend;
    }

    public void changePlace(String name) {
        dbRef.child("Places").child("BCC").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                place.postValue(snapshot.getValue(String.class));
                Toast.makeText(application, "PLACE11111" + snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error_Place: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
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
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(application.getApplicationContext(),
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) application.getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

    public MutableLiveData<User> getUser() { return user; }

    public MutableLiveData<User> getSearchUser() { return searchUser; }

    public MutableLiveData<ArrayList<User>> getUserRequest() { return userRequest; }

    public MutableLiveData<Bitmap> getPicture() { return picture; }

    public MutableLiveData<Bitmap> getSearchPicture() { return searchPicture; }

    public MutableLiveData<ArrayList<String>> getPlaces() { return places; }

    public MutableLiveData<String> getPlace() { return place; }

    public MutableLiveData<ArrayList<Integer>> getLocation() { return location; }

    public MutableLiveData<ArrayList<String>> getFriends() { return friends; }

    public MutableLiveData<ArrayList<String>> getRequests() { return requests; }
}
