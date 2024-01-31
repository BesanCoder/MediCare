package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityMedicine extends AppCompatActivity {

    private Button btnAdd;
    private Button btnDetails;
    private BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);

        initializeViews();

        btnAdd.setOnClickListener(c -> startActivity(new Intent(MainActivityMedicine.this, AddMedicine.class)));
        btnDetails.setOnClickListener(c -> startActivity(new Intent(MainActivityMedicine.this, ListRV.class)));

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

    private void initializeViews() {
        btnAdd = findViewById(R.id.addbtn);
        btnDetails = findViewById(R.id.detailsbtn);
        bottomNavi = findViewById(R.id.bottomnavi);
    }
}
