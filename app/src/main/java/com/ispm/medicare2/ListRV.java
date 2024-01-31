package com.ispm.medicare2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListRV extends AppCompatActivity {

    private static final String TAG = "ListRV";

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Medicine, MedicineView> adapter;
    private DatabaseReference medicinesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rv);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medicare2-75841-default-rtdb.europe-west1.firebasedatabase.app");
        medicinesRef = database.getReference("medicines");

        FirebaseRecyclerOptions<Medicine> options =
                new FirebaseRecyclerOptions.Builder<Medicine>()
                        .setQuery(medicinesRef, new SnapshotParser<Medicine>() {
                            @NonNull
                            @Override
                            public Medicine parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Medicine med = snapshot.getValue(Medicine.class);
                                if (med != null) {
                                    med.setKey(snapshot.getKey());
                                }
                                return med;
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Medicine, MedicineView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MedicineView holder, int position, @NonNull Medicine model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public MedicineView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
                return new MedicineView(view);
            }
        };

        recyclerView.setAdapter(adapter);

        Log.d(TAG, "onCreate: Adapter set up");
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        Log.d(TAG, "onStart: Adapter started listening");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

        Log.d(TAG, "onStop: Adapter stopped listening");
    }
}
