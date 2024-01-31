package com.ispm.medicare2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Bookingdr extends AppCompatActivity {

    EditText dateTXT;
    ImageView cal;
    private int mDate, mMonth, mYear;

    Button savebutton;
    EditText nameTXT;
    EditText phonenumberTXT;
    EditText reasonTXT;
    FirebaseFirestore db;
    Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdr);

        db = FirebaseFirestore.getInstance();

        timeTXT = findViewById(R.id.timeButton);
        timepicker = findViewById(R.id.timepicker);
        nameTXT = findViewById(R.id.name);
        phonenumberTXT = findViewById(R.id.phonenumber);
        reasonTXT = findViewById(R.id.reason);
        savebutton = findViewById(R.id.savebutton);
        appointment = new Appointment();

        // Save data to Firestore
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber = phonenumberTXT.getText().toString().trim();
                String reason = reasonTXT.getText().toString().trim();
                String date = dateTXT.getText().toString().trim();
                String time = timeTXT.getText().toString().trim();

                Map<String, Object> appointmentData = new HashMap<>();
                appointmentData.put("name", nameTXT.getText().toString().trim());
                appointmentData.put("phonenumber", phonenumber);
                appointmentData.put("reason", reason);
                appointmentData.put("date", date);
                appointmentData.put("time", time);

                // Save the data to Firestore
                db.collection("Appointments")
                        .add(appointmentData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Bookingdr.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Bookingdr.this, "Error inserting data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // Datepicker
        dateTXT = findViewById(R.id.date);
        cal = findViewById(R.id.datepicker);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bookingdr.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int date) {
                        dateTXT.setText(date + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.getDatePicker().setMinDate(Cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    // Timepicker
    EditText timeTXT;
    ImageView timepicker;
    int hour, minute;

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeTXT.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    // Click button to generate report and send the data
    public void buttonsenderpressed(View v) {
        Intent senderIntent = new Intent(this, com.ispm.medicare2.Report.class);
        senderIntent.putExtra("date", dateTXT.getText().toString());
        senderIntent.putExtra("time", timeTXT.getText().toString());
        senderIntent.putExtra("name", nameTXT.getText().toString());
        senderIntent.putExtra("phonenumber", phonenumberTXT.getText().toString());
        senderIntent.putExtra("reason", reasonTXT.getText().toString());
        startActivity(senderIntent);
    }
}