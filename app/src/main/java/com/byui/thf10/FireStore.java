package com.byui.thf10;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class FireStore {

    private final FirebaseFirestore db;

    FireStore(FirebaseFirestore db) {
        this.db = db;
    }

    public void setup() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    /**
     * Purpose is to push data into the cloud. This could have been created into an interface
     * and perhaps may have been more efficient that way.
     * @param list Is the JsonCovertibles that are sent to the Firebase.
     */

    public void storeJson(List<JsonConvertible> list, String collection) {

        CollectionReference collectionReference = db.collection(collection);

        for (JsonConvertible i : list) {
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

    /**
     * Purpose of this is to pull data from specific collections in the firebase API.
     * @param collection The selected collection in the firebase that shoudl be pulled from.
     */

    public void pullCollection(final String collection, final String c, final CallBackList callBackList) {
        CollectionReference collectionReference = db.collection(collection);
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Object> list = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                try {
                                    Object obj = doc.toObject(Class.forName(c));
                                    list.add(obj);
                                    Log.d(TAG, "data added" + doc.toString());
                                } catch (ClassNotFoundException e) {
                                    Log.d(TAG, "No such class");
                                }
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
