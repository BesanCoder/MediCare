package com.ispm.medicare2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Action {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    public Action()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Medicine.class.getSimpleName());
    }
    public Task<Void> add(Medicine med)
    {
        return databaseReference.push().setValue(med);
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key)
    {
        if(key == null)
        {
            return databaseReference.orderByKey();
        }
        return databaseReference.orderByKey().startAfter(key);
    }

    public Query get()
    {
        return databaseReference;
    }
}

