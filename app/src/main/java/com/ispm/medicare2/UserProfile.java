package com.ispm.medicare2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Button btnDeleteProfile = findViewById(R.id.btnDeleteProfile);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);

        // Delete user profile
        btnDeleteProfile.setOnClickListener(v -> showDeleteConfirmationDialog());

        // Cancel button
        btnCancel.setOnClickListener(v -> {
            Intent cancel = new Intent(UserProfile.this, Home.class);
            startActivity(cancel);
            finish();
        });

        // Edit profile button
        final String phoneNumber = "0793746578";
        btnEditProfile.setOnClickListener(v -> openDialerWithPhoneNumber(phoneNumber));
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Are you sure?");
        dialog.setMessage("Deleting this account will remove your email from this app");
        dialog.setPositiveButton("Delete", (dialogInterface, which) -> {
            // Remove the email from Firebase Authentication
            firebaseUser.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(UserProfile.this, "Email removed. You have been signed out.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(UserProfile.this, "Failed to delete user: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void openDialerWithPhoneNumber(String phoneNumber) {
        // Open the dialer app with the specific phone number
        Intent dialerIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phoneNumber)));
        startActivity(dialerIntent);
    }
}
