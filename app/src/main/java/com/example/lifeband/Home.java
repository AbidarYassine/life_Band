package com.example.lifeband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeband.db.ConnexionDb;
import com.example.lifeband.models.Guardian;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {
    Button updatebtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refBPM;
    DatabaseReference refTemp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView text_view_BPM;
    TextView text_view_temp;
    private static final String TAG = "Home";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updatebtn = findViewById(R.id.updatebtn);
        text_view_BPM = findViewById(R.id.text_view_BPM);
        text_view_temp = findViewById(R.id.text_view_temp);
//        String uid = FirebaseAuth.getInstance().getCurrentUser();
//        Guardian guardian = FirebaseDatabase.getInstance().getReference().child("Guardians");
//        ConnexionDb.db().collection("").whereEqualTo();
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
//                if ()
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
        Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
    }
}