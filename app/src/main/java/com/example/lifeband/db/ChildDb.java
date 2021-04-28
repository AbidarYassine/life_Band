package com.example.lifeband.db;

import android.util.Log;

import com.example.lifeband.callback.GetChild;
import com.example.lifeband.models.Child;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
}
