package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private CardView profile, medicine, reminder, journal, appointment, logout;
    private BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profile = findViewById(R.id.profile);
        medicine = findViewById(R.id.medicine);
        reminder = findViewById(R.id.reminder);
        journal = findViewById(R.id.journal);
        appointment = findViewById(R.id.appointment);
        logout = findViewById(R.id.logout);

        profile.setOnClickListener(this);
        medicine.setOnClickListener(this);
        reminder.setOnClickListener(this);
        journal.setOnClickListener(this);
        appointment.setOnClickListener(this);
        logout.setOnClickListener(this);

        bottomNavi = findViewById(R.id.bottomnavi);

        bottomNavi.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.reminder) {
                startActivity(new Intent(getApplicationContext(), MainActivityReminder.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                overridePendingTransition(0, 0);
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int viewId = v.getId();

        if (viewId == R.id.profile) {
            intent = new Intent(this, UserProfile.class);
        } else if (viewId == R.id.medicine) {
            intent = new Intent(this, MainActivityMedicine.class);
        } else if (viewId == R.id.reminder) {
            intent = new Intent(this, MainActivityReminder.class);
        } else if (viewId == R.id.journal) {
            intent = new Intent(this, MainActivityJournal.class);
        } else if (viewId == R.id.appointment) {
            intent = new Intent(this, MainActivityAppointment.class);
        } else if (viewId == R.id.logout) {
            intent = new Intent(this, SignIn.class);
            finish(); // Assuming you want to finish the current activity after logging out
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
