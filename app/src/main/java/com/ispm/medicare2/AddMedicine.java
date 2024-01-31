package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMedicine extends AppCompatActivity {

    private DatabaseReference medicinesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        // Initialize Firebase
        initializeFirebase();

        // Initialize UI elements
        EditText addnameMed = findViewById(R.id.addnameMed);
        EditText addtypeMed = findViewById(R.id.addtypeMed);
        EditText adduseMed = findViewById(R.id.adduseMed);
        EditText adddose = findViewById(R.id.adddose);
        EditText addintake = findViewById(R.id.addintake);
        EditText addinfo = findViewById(R.id.addinfo);
        Button btnsave = findViewById(R.id.savebtn);
        Button btnlist = findViewById(R.id.listbtn);

        // Set click listener for save button
        btnsave.setOnClickListener(view -> saveMedicine(addnameMed, addtypeMed, adduseMed, adddose, addintake, addinfo));

        // Set click listener for list button
        btnlist.setOnClickListener(view -> openMedicineList());
    }

    private void initializeFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medicare2-75841-default-rtdb.europe-west1.firebasedatabase.app");
        medicinesRef = database.getReference("medicines");
    }

    private void saveMedicine(EditText addnameMed, EditText addtypeMed, EditText adduseMed,
                              EditText adddose, EditText addintake, EditText addinfo) {
        // Check for empty fields
        if (areFieldsEmpty(addnameMed, addtypeMed, adduseMed, adddose, addintake, addinfo)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Medicine object with user input
        Medicine med = new Medicine(
                addnameMed.getText().toString(),
                addtypeMed.getText().toString(),
                adduseMed.getText().toString(),
                adddose.getText().toString(),
                addintake.getText().toString(),
                addinfo.getText().toString()
        );

        // Save the Medicine object to Firebase
        saveMedicineToFirebase(med);
    }

    private boolean areFieldsEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                return true;
            }
        }
        return false;
    }

    private void saveMedicineToFirebase(Medicine med) {
        medicinesRef.push().setValue(med)
                .addOnSuccessListener(suc -> {
                    startActivity(new Intent(getApplicationContext(), ListRV.class));
                    Toast.makeText(this, "SAVED!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(er -> {
                    Toast.makeText(this, "Error: " + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void openMedicineList() {
        Intent intent = new Intent(AddMedicine.this, ListRV.class);
        startActivity(intent);
    }
}
