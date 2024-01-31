package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Report extends AppCompatActivity {

    TextView namereceived;
    TextView phonenumberreceived;
    TextView reasonreceived;
    TextView timereceived;
    TextView datereceived;

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        namereceived = findViewById(R.id.namereport);
        phonenumberreceived = findViewById(R.id.phonenum);
        reasonreceived = findViewById(R.id.reasonreport);
        timereceived = findViewById(R.id.timereport);
        datereceived = findViewById(R.id.datereport);

        bottomNavi = findViewById(R.id.bottomnavi);

        // receive data from Bookingdr
        Intent receiverIntent = getIntent();
        String namereceivedValue = receiverIntent.getStringExtra("name");
        String phonenumberreceivedValue = receiverIntent.getStringExtra("phonenumber");
        String reasonreceivedValue = receiverIntent.getStringExtra("reason");
        String timereceivedValue = receiverIntent.getStringExtra("time");
        String datereceivedValue = receiverIntent.getStringExtra("date");

        // set default values if null
        namereceived.setText(namereceivedValue != null ? namereceivedValue : "Unprovided");
        phonenumberreceived.setText(phonenumberreceivedValue != null ? phonenumberreceivedValue : "Unprovided");
        reasonreceived.setText(reasonreceivedValue != null ? reasonreceivedValue : "Unprovided");
        timereceived.setText(timereceivedValue != null ? timereceivedValue : "Unprovided");
        datereceived.setText(datereceivedValue != null ? datereceivedValue : "Unprovided");

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
}
