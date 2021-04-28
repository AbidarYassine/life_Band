package com.example.lifeband.db;

import android.util.Log;

import com.example.lifeband.callback.GetGuardiant;
import com.example.lifeband.models.Guardian;
import com.google.firebase.firestore.DocumentSnapshot;

public class GuardiantDb {
    private static final String TAG = "GuardiantDb";

    public static void getGuardiant(GetGuardiant getGuardiantCallBack, String uid) {
        ConnexionDb.db().collection("Guardians").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Guardian guardian = document.toObject(Guardian.class);
                        getGuardiantCallBack.onComplete(guardian);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
