package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityReminder extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_main);

        initializeViews();

        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            Log.d(TAG, "onSelectedDayChange: date " + date);

            Intent intent = new Intent(MainActivityReminder.this, TimeActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }

    private void initializeViews() {
        calendarView = findViewById(R.id.calendarView);
    }
}
