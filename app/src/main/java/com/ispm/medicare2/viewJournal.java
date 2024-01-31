package com.ispm.medicare2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class viewJournal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal);

        ListView listView = findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(viewJournal.this, R.layout.list_item, list);
        listView.setAdapter(adapter);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Journal")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            Journal j = document.toObject(Journal.class);
                            if (j != null) {
                                String txt = " Title: " + j.getTitle() + " Date: " + j.getDate() + " Notes: " + j.getNotes();
                                list.add(txt);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(viewJournal.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
                    }
                });

        ImageButton btn = findViewById(R.id.addjournal);
        btn.setOnClickListener(v -> startActivity(new Intent(viewJournal.this, com.ispm.medicare2.MainActivityJournal.class)));
    }
}
