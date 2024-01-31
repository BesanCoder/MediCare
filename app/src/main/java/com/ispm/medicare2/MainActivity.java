package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnSignIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
            startActivity(signUpIntent);
        });

        btnSignIn.setOnClickListener(v -> {
            Intent signInIntent = new Intent(MainActivity.this, SignIn.class);
            startActivity(signInIntent);
            finish();
        });
    }

    private void initializeViews() {
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        TextView slogan = findViewById(R.id.slogan);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is already signed in, handle accordingly.
        }
    }
}
