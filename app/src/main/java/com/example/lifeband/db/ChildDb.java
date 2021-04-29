package com.example.lifeband.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lifeband.callback.GetChild;
import com.example.lifeband.models.Child;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ChildDb {
    private static final String TAG = "ChildDb";

    public static void getChildByGuarId(GetChild getChildCallBack, String guard_id) {
        ConnexionDb.db().collection("childs")
                .whereEqualTo("guardians_id", guard_id)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Child child = document.toObject(Child.class);
                            child.setId(document.getId());
                            getChildCallBack.onComplete(child);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public static void addChild(Child child) {
        Map<String, Object> childToSave = new HashMap<>();
        childToSave.put("guardians_id", child.getGuardians_id());
        childToSave.put("history", child.getHistory());
        ConnexionDb.db().collection("childs")
                .add(childToSave)
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }

}
