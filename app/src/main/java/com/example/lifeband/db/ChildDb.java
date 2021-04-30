package com.example.lifeband.db;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lifeband.callback.GetChild;
import com.example.lifeband.models.Child;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    public static void updateHistory(Child child, String valueBpm, String valueTemp) {
        List<Map<String, String>> history = child.getHistory();
        Map<String, String> data = new HashMap<>();
        data.put("BPM", valueBpm);
        data.put("TEMP", valueTemp);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy H:mm:ss");
        String strDate;
        strDate = formatter.format(date);
        data.put("date", strDate);
        history.add(data);
        child.setHistory(history);
        Map<String, Object> t = new HashMap<>();
        t.put("guardians_id", child.getGuardians_id());
        t.put("history", history);
        CollectionReference complaintsRef = ConnexionDb.db().collection("childs");
        complaintsRef.document(child.getId()).set(t, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "updateHistory: ok");
            } else {
                Log.i(TAG, "updateHistory: erreur");
            }
        });
    }


}
