package com.ispm.medicare2;

import com.ispm.medicare2.model.UserSignUp;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DBUser {

    private DatabaseReference databaseReference;

    public DBUser()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(UserSignUp.class.getSimpleName());
    }

    public Task<Void> add(UserSignUp us)
    {
        return databaseReference.push().setValue(us);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }

}
