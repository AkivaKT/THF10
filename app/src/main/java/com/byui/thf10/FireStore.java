package com.byui.thf10;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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


    public void storeJson(List<JsonConvertible> Account) {

        CollectionReference collectionReference = db.collection("Account");

        for (JsonConvertible i : Account) {
            collectionReference.add(i)
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

    public void pullCollection(final String collection, final CallBackList callBackList) {
        CollectionReference collectionReference = db.collection(collection);

        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<JsonConvertible> list = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                JsonConvertible obj = doc.toObject(JsonConvertible.class);
                                list.add(obj);
                                Log.d(TAG, "account added" + doc.toString());
                            }
                            callBackList.onCallback(list);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "fail to load collection for" + collection);
                    }
                });
    }
}
