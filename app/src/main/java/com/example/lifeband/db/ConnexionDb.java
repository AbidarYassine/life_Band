package com.example.lifeband.db;

import com.google.firebase.firestore.FirebaseFirestore;


public abstract class ConnexionDb {

    public static FirebaseFirestore db() {
        return FirebaseFirestore.getInstance();
    }
}
