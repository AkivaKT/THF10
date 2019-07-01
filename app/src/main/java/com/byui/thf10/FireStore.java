package com.byui.thf10;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FireStore {

    private final FirebaseFirestore db;
    private DatabaseReference mDatabase;
    private String Product;
    private String Sales;
    private String Price;

    public FireStore(FirebaseFirestore db) {
        this.db = db;
    }

    public void setup() {

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }


    public void StoreJson(List<JsonConvertible> Product) {

        CollectionReference myRef = db.collection("Product");
        Map<String, Object> Update = new HashMap<>();
        Update.put("Product", Product);
        // Update.put("Sales", Sales);
        // Update.put("Price", Price);

        for (JsonConvertible i : Product) {

            myRef.add(i)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot successfully written!" + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }

    }

}

