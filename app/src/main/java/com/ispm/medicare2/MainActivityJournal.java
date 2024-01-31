package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityJournal extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private Journal journal;
    private BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_main);

        initializeViews();

        EditText date = findViewById(R.id.date);
        EditText title = findViewById(R.id.title);
        EditText notes = findViewById(R.id.notes);
        Button button = findViewById(R.id.adddata);
        Button view = findViewById(R.id.viewJournal);

        journal = new Journal();
        firestore = FirebaseFirestore.getInstance();

        button.setOnClickListener(v -> {
            if (isFieldEmpty(date) || isFieldEmpty(title) || isFieldEmpty(notes)) {
                showToast("Please fill in all fields");
            } else {
                journal.setTitle(getStringFromEditText(title));
                journal.setDate(getStringFromEditText(date));
                journal.setNotes(getStringFromEditText(notes));

                addJournalToFirestore();
            }
        });

        view.setOnClickListener(v -> startActivity(new Intent(MainActivityJournal.this, viewJournal.class)));

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
        bottomNavi = findViewById(R.id.bottomnavi);
    }

    private boolean isFieldEmpty(EditText editText) {
        return getStringFromEditText(editText).isEmpty();
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void showToast(String message) {
        Toast.makeText(MainActivityJournal.this, message, Toast.LENGTH_SHORT).show();
    }

    private void addJournalToFirestore() {
        firestore.collection("Journal")
                .add(journal)
                .addOnSuccessListener(documentReference -> {
                    showToast("Journal Added Successfully");
                    startActivity(new Intent(MainActivityJournal.this, viewJournal.class));
                })
                .addOnFailureListener(e -> showToast("Error: " + e.getMessage()));
    }
}
