package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ispm.medicare2.model.UserSignUp;


public class SignUp extends AppCompatActivity {

    EditText fullName, username, email, password, phone, address;
    Button btnSignUp;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = (EditText) findViewById(R.id.fullName);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);


        reference = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullN = fullName.getText().toString();
                String userN = username.getText().toString();
                String em = email.getText().toString();
                String pho = phone.getText().toString();
                String add = address.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(fullN)) {
                    Toast.makeText(SignUp.this, "Full name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userN)) {
                    Toast.makeText(SignUp.this, "Username is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(em)) {
                    Toast.makeText(SignUp.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pho)) {
                    Toast.makeText(SignUp.this, "Phone number is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(add)) {
                    Toast.makeText(SignUp.this, "Address is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pass)) {
                    Toast.makeText(SignUp.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.length()<6) {
                    Toast.makeText(SignUp.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserSignUp userSignUp = new UserSignUp(fullN, userN, em, pho, add, pass);

                                    firebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(userSignUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(SignUp.this, "Registration completed", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                    finish();
                                                }
                                            });

                                } else {

                                }
                            }
                        });
            }
        });
    }
}