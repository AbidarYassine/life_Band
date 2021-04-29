package com.example.lifeband.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifeband.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update extends AppCompatActivity {

    // Declare variables.
    EditText EmailText, NameText, AgeText;
    CheckBox CB1, CB2, CB3, CB4, CB5, CB6;
    Button buttonUpdate;

    //Declare an instance of FirebaseAuthentication and FirebaseFirestore for cloud
    FirebaseAuth databaseAuth;
    FirebaseFirestore dataStore;
    FirebaseUser UpdateGuardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setTitle("Update");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Fitch each variable with what component fits in the interface.
        EmailText = findViewById(R.id.email_update);
        NameText = findViewById(R.id.UpdateName);
        AgeText = findViewById(R.id.UpdateAge);

        CB1 = findViewById(R.id.checkBox1U);
        CB2 = findViewById(R.id.checkBox2U);
        CB3 = findViewById(R.id.checkBox3U);
        CB4 = findViewById(R.id.checkBox4U);
        CB5 = findViewById(R.id.checkBox5U);
        CB6 = findViewById(R.id.checkBox6U);

        buttonUpdate = findViewById(R.id.Savebtn);

        //initialize the FirebaseAuthentication and FirebaseFirestore instance.
        databaseAuth = FirebaseAuth.getInstance();
        dataStore = FirebaseFirestore.getInstance();
        UpdateGuardian = databaseAuth.getCurrentUser();

        //When the user click on Save button then
        buttonUpdate.setOnClickListener(v -> {

            String emailDB = EmailText.getText().toString();
            String nameDB = NameText.getText().toString();
            String ageDB = AgeText.getText().toString();

            List DiseaseArray = new ArrayList();
            if (CB1.isChecked()) DiseaseArray.add("Diabetes");
            if (CB2.isChecked()) DiseaseArray.add("Blood Pressure");
            if (CB3.isChecked()) DiseaseArray.add("Anemia");
            if (CB4.isChecked()) DiseaseArray.add("Asthma");
            if (CB5.isChecked()) DiseaseArray.add("Epilepsy");
            if (CB6.isChecked()) DiseaseArray.add("Heart failure");

            // Check if one of the field is empty and send the error message
            if (nameDB.isEmpty() || ageDB.isEmpty() || emailDB.isEmpty()) {
                Toast.makeText(Update.this, "Enter information to Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            // Check evey field if the input is wrongy and send the error message
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(EmailText.getText());

            if (!matcher.find()) {
                EmailText.setError("Invalid Email");
                return;
            }
            if (!NameText.getText().toString().matches("[a-zA-Z]+")) {
                NameText.setError("Name can not be numbers");
                return;
            }
            if (!AgeText.getText().toString().matches("[0-9]+")) {
                AgeText.setError("Age should be numbers ");
                return;
            }

            //By this method will Update the user information in the databae
            UpdateGuardian.updateEmail(emailDB).addOnSuccessListener(aVoid -> {
                DocumentReference documentRef = dataStore.collection("Guardians").document(UpdateGuardian.getUid());
                Map<String, Object> edit = new HashMap<>();
                edit.put("Email", emailDB);
                edit.put("ChildName", nameDB);
                edit.put("ChildAge", ageDB);
                edit.put("disease", DiseaseArray);

                documentRef.update(edit).addOnSuccessListener(aVoid1 -> {
                    // Successful message and display the Home Page
                    Toast.makeText(Update.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                });
            });

        });
    }
}