package com.example.trybil.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthRepository {
    private final Application application;
    private final FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<Boolean> userLoggedMutableLiveData;
    private final DatabaseReference dbRef;
    private static AuthRepository authRepositorySingleton;

    public static AuthRepository getInstance(Application application) {
        if(authRepositorySingleton != null) {
            return authRepositorySingleton;
        }
        else {
            return new AuthRepository(application);
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    private AuthRepository(Application application){
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        //MainRepository.getInstance(application);

        if (auth.getCurrentUser() != null){
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public void register(String email , String pass, String username, String department){
        auth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    User user = new User(email, username, department);

                    // Add user to realtime database
                    dbRef.child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
                    dbRef.child("Usernames").child(username).setValue(auth.getUid());
                }
                else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login(String email , String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    Toast.makeText(application, "Successful Login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(application, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signOut(){
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }

    public void loginAnon() {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(application, "Logged in anonymously", Toast.LENGTH_SHORT).show();
                    //FirebaseUser user = auth.getCurrentUser();
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(application, "Anonymous login failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    firebaseUserMutableLiveData.postValue(null);
                }
            }
        });
    }
}
