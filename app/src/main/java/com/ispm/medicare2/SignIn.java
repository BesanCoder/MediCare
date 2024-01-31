package com.ispm.medicare2;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class SignIn extends AppCompatActivity {

    private static final int REQUEST_CODE = 101010;
    private EditText email, password;
    private Button btnSignIn;
    private ImageView fingerprint;
    private TextView textViewFingerprint, textViewOR;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeViews();
        initializeBiometricAuthentication();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        btnSignIn.setOnClickListener(v -> {
            String em = email.getText().toString();
            String pass = password.getText().toString();

            if (TextUtils.isEmpty(em) || TextUtils.isEmpty(pass)) {
                Toast.makeText(SignIn.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            performAuth(em, pass);
        });

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            textViewOR.setVisibility(View.VISIBLE);
            textViewFingerprint.setVisibility(View.VISIBLE);
            fingerprint.setVisibility(View.VISIBLE);
        }
    }

    private void initializeViews() {
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        btnSignIn = findViewById(R.id.btnSignIn);
        fingerprint = findViewById(R.id.fingerprint);
        textViewFingerprint = findViewById(R.id.textViewFingerprint);
        textViewOR = findViewById(R.id.textViewOR);
    }

    private void initializeBiometricAuthentication() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");

                executor = ContextCompat.getMainExecutor(this);
                biometricPrompt = new BiometricPrompt(SignIn.this,
                        executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(getApplicationContext(),
                                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        String em = sharedPreferences.getString("email", "");
                        String pass = sharedPreferences.getString("password", "");

                        performAuth(em, pass);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric login for my app")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Use account password")
                        .build();

                fingerprint.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // Fingerprint sensor does not exist
                // Handle this case (e.g., show a different login option)
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // Sensor not available
                // Handle this case (e.g., show a different login option)
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Biometric features are not enrolled on the device
                // Handle this case (e.g., show a different login option)
                break;
        }
    }

    private void performAuth(String em, String pass) {
        progressDialog.setMessage("Sign In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("email", em);
                        editor.putString("password", pass);
                        editor.putBoolean("isLogin", true);
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(), Home.class));
                        progressDialog.dismiss();
                        Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignIn.this, "Sign in failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
