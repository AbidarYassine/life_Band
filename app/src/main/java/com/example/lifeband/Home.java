package com.example.lifeband;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeband.db.ConnexionDb;
import com.example.lifeband.models.Child;
import com.example.lifeband.models.Guardian;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends AppCompatActivity {
    Button updatebtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refBPM;
    DatabaseReference refTemp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView text_view_BPM;
    TextView text_view_temp;
    private static final String TAG = "Home";
    Guardian guardian;
    Child child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updatebtn = findViewById(R.id.updatebtn);
        text_view_BPM = findViewById(R.id.text_view_BPM);
        text_view_temp = findViewById(R.id.text_view_temp);
        // get uid
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // get all info by uid
        ConnexionDb.db().collection("Guardians").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        guardian = document.toObject(Guardian.class);
                        Log.i(TAG, "onCreate: " + guardian.toString());
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
//        ConnexionDb.db().collection("childs")
//                .whereEqualTo("Email", "lifeband@gmail.com")
//                .limit(1)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            child = document.toObject(Child.class);
//                            Log.i(TAG, "onComplete: " + child.toString());
//                        }
//                    } else {
//                        Log.d(TAG, "Error getting documents: ", task.getException());
//                    }
//                });
        updatebtn.setOnClickListener(view -> {
            Intent Intent3 = new Intent(Home.this, Update.class);
            startActivity(Intent3);
        });
        refBPM = database.getReference("BPM");
        refTemp = database.getReference("Temp");
        refBPM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get data BPM.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                String v = value + " BPM";
                if (Integer.getInteger(guardian.getChildAge()) > 10) {
                    // TODO  finish this topic
                }
                text_view_BPM.setText(v);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        refTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                String v = value + "C " + "°";
                text_view_temp.setText(v);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}