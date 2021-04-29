package com.example.lifeband.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lifeband.R;
import com.example.lifeband.db.ChildDb;
import com.example.lifeband.db.GuardiantDb;
import com.example.lifeband.utils.CustomPopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home extends AppCompatActivity {
    Button updatebtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refBPM;
    DatabaseReference refTemp;
    TextView text_view_BPM;
    TextView text_view_temp;
    ImageView imageBPM;
    ImageView imageTemp;
    private static final String TAG = "Home";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // setup view get all view by Id
        setupView();
        updatebtn.setOnClickListener(view -> {
            Intent Intent3 = new Intent(Home.this, Update.class);
            startActivity(Intent3);
        });
        refBPM = database.getReference("BPM");
        refTemp = database.getReference("Temp");
        // get uid
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // get guard by uid
        GuardiantDb.getGuardiant(guard -> {
            // get child by uid and BPM ,TEMP to update history
            ChildDb.getChildByGuarId(child -> {
                Log.i(TAG, "onCreate: child " + child.toString());
                refBPM.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get data BPM.
                        String valueBpm = dataSnapshot.getValue(String.class);

                        String v = valueBpm + " BPM";
                        String age = guard.getChildAge();
                        int BPM = Integer.parseInt(valueBpm);
                        // send age and BPM to another method for treatment
                        checkBPM(age, BPM);
                        text_view_BPM.setText(v);
                        ChildDb.updateHistoryBPM(child, valueBpm);
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
                        String valueTEMP = dataSnapshot.getValue(String.class);
                        String v = valueTEMP + " C " + "°";
                        String age = guard.getChildAge();
                        double TEMP = Double.parseDouble(valueTEMP);
                        // send age and BPM to another method for treatment
                        checkTEMP(age, TEMP);
                        text_view_temp.setText(v);
                        ChildDb.updateHistoryTEMP(child, valueTEMP);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }, uid);

        }, uid);


    }


    private void setupView() {
        updatebtn = findViewById(R.id.updatebtn);
        text_view_BPM = findViewById(R.id.text_view_BPM);
        text_view_temp = findViewById(R.id.text_view_temp);
        imageBPM = findViewById(R.id.imageBPM);
        imageTemp = findViewById(R.id.imageTemp);
    }

    // check BPM state
    private void checkBPM(String age, int BPM) {
        if (age.equalsIgnoreCase("1mo - 1year")) {
            if (BPM >= 80 && BPM <= 160) {
                showNormalBPM();
            } else {
                showAlertBPM();
            }
        } else if (age.equalsIgnoreCase("1-3 year")) {
            if (BPM >= 80 && BPM <= 130) {
                showNormalBPM();
            } else {
                showAlertBPM();
            }
        } else if (age.equalsIgnoreCase("3-6 year")) {
            if (BPM >= 80 && BPM <= 120) {
                showNormalBPM();
            } else {
                showAlertBPM();
            }
        } else if (age.equalsIgnoreCase("6-12 year")) {
            if (BPM >= 65 && BPM <= 100) {
                showNormalBPM();
            } else {
                showAlertBPM();
            }
        } else if (age.equalsIgnoreCase("12-18 year")) {
            if (BPM >= 60 && BPM <= 90) {
                showNormalBPM();
            } else {
                showAlertBPM();
            }
        }

    }

    // check TEMP state
    private void checkTEMP(String age, double TEMP) {
        if (age.equalsIgnoreCase("1mo - 1year")) {
            if (TEMP >= 37.44 && TEMP <= 37.61) {
                showNormalTemp();
            } else {
                showAlertTEMP();
            }
        } else if (age.equalsIgnoreCase("1-3 year")) {
            if (TEMP >= 37.44 && TEMP <= 37.61) {
                showNormalTemp();
            } else {
                showAlertTEMP();
            }
        } else if (age.equalsIgnoreCase("3-6 year")) {
            if (TEMP >= 37 && TEMP <= 37.22) {
                showNormalTemp();
            } else {
                showAlertTEMP();
            }
        } else if (age.equalsIgnoreCase("6-12 year")) {
            if (TEMP == 37) {
                showNormalTemp();
            } else {
                showAlertTEMP();
            }
        } else if (age.equalsIgnoreCase("12-18 year")) {
            if (TEMP >= 36.11 && TEMP <= 37.22) {
                showNormalTemp();
            } else {
                showAlertTEMP();
            }
        }
    }

    // show image alert BPM
    private void showAlertBPM() {
        notification();
        openDialog();
        imageBPM.setImageResource(R.drawable.heartalert);
    }

    // show image for normal BPM
    private void showNormalBPM() {
        imageBPM.setImageResource(R.drawable.heartpic);
    }

    // show image Alert Temp
    private void showAlertTEMP() {
        notification();
        openDialog();
        imageTemp.setImageResource(R.drawable.tempalert);
    }

    // show image for normal Temp
    private void showNormalTemp() {
        imageTemp.setImageResource(R.drawable.timpic);
    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("guard", "guard", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService((NotificationManager.class));
            notificationManager.createNotificationChannel(channel);
        }
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "guard")
                .setContentText("Life Band Notification")
                .setSmallIcon(R.drawable.heartalert)
                .setVibrate(pattern)
                .setSound(alarmSound, AudioManager.STREAM_NOTIFICATION)
                .setAutoCancel(false)
                .setContentText("Test Content");
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(this);
        notificationCompat.notify(999, builder.build());
    }

    public void openDialog() {
        CustomPopup customPopup = new CustomPopup(this);
        customPopup.setTitle("Alert !!");
        customPopup.setContent("Your Child ...");
        customPopup.build();
        customPopup.getButton().setOnClickListener(v -> {
            customPopup.dismiss();
        });
    }

    public void gotoHistory(View view) {
        Intent intent = new Intent(this, History.class);
        intent.putExtra("current_temp", text_view_temp.getText().toString());
        intent.putExtra("current_bpm", text_view_BPM.getText().toString());
        startActivity(intent);
    }
}