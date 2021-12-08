package com.example.trybil.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trybil.databinding.ActivityAuthBinding;
import com.example.trybil.viewmodel.AuthViewModel;

public class AuthActivity extends AppCompatActivity {
    AuthViewModel authViewModel;
    ActivityAuthBinding activityAuthBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        /* Create view objects
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignup = findViewById(R.id.buttonSignup);
        Button buttonHelp = findViewById(R.id.buttonHelp);
        Button buttonLoginAnon = findViewById(R.id.buttonLoginAnon);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        // Add Listener to the buttons
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail.setText("attemptEmail");


               or

                activityLoginBinding.editTextEmail.setText("attempt2");
                Toast.makeText(getApplicationContext(),"TryToast", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("EXTRA", "extra");
                startActivity(intent);
            }
        });
         */

        // Binding use to access View objects instead of findById
        activityAuthBinding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(activityAuthBinding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }
}