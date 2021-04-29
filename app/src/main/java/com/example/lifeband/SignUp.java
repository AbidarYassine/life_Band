package com.example.lifeband;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifeband.db.ChildDb;
import com.example.lifeband.models.Child;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    public static final String TAG = "TAG";
    // Declare variables.
    EditText PasswordText, EmailText, NameText;
    Spinner age;
    CheckBox CB1, CB2, CB3, CB4, CB5, CB6;
    Button buttonS;

    //Declare an instance of FirebaseAuthentication and FirebaseFirestore for cloud
    FirebaseAuth databaseAuth;
    FirebaseFirestore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Fitch each variable with what component fits in the interface.
        EmailText = findViewById(R.id.email_rg);
        PasswordText = findViewById(R.id.password_rg);
        NameText = findViewById(R.id.name_rg);
        age = findViewById(R.id.age_signup);

        CB1 = findViewById(R.id.checkBox1);
        CB2 = findViewById(R.id.checkBox2);
        CB3 = findViewById(R.id.checkBox3);
        CB4 = findViewById(R.id.checkBox4);
        CB5 = findViewById(R.id.checkBox5);
        CB6 = findViewById(R.id.checkBox6);

        buttonS = findViewById(R.id.reg_b2);

        //initialize the FirebaseAuthentication and FirebaseFirestore instance.
        databaseAuth = FirebaseAuth.getInstance();
        dataStore = FirebaseFirestore.getInstance();

        //When the user click on Sign Up button then
        buttonS.setOnClickListener(v -> {

            String emailDB = EmailText.getText().toString();
            String passwordDB = PasswordText.getText().toString();
            String nameDB = NameText.getText().toString();
            String ageDB = age.getSelectedItem().toString();

            List DiseaseArray = new ArrayList();
            if (CB1.isChecked()) DiseaseArray.add("Diabetes");
            if (CB2.isChecked()) DiseaseArray.add("Blood Pressure");
            if (CB3.isChecked()) DiseaseArray.add("Anemia");
            if (CB4.isChecked()) DiseaseArray.add("Asthma");
            if (CB5.isChecked()) DiseaseArray.add("Epilepsy");
            if (CB6.isChecked()) DiseaseArray.add("Heart failure");

            // Check evey field if the input is wrongy and send the error message
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(EmailText.getText());
            if (TextUtils.isEmpty(emailDB) || TextUtils.isEmpty(passwordDB) || TextUtils.isEmpty(nameDB) || TextUtils.isEmpty(ageDB)) {
                EmailText.setError("This field can not be empty ");
                return;
            }

            if (!matcher.find()) {
                EmailText.setError("Invalid email");
                return;
            }

            if (!NameText.getText().toString().matches("[a-zA-Z]+")) {
                NameText.setError("Name can not be numbers");
                return;
            }
//            if (!AgeText.getText().toString().matches("[0-9]+")){
//                AgeText.setError("Age should be numbers ");
//                return;
//            }

            // Create account for the user by the Email and password in FirebaseAuthentication
            databaseAuth.createUserWithEmailAndPassword(emailDB, passwordDB).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // if it is created Successfully then add the rest of the input in the cloud database
                    String GuardiansID = databaseAuth.getCurrentUser().getUid();
                    //Cloud Firestore creates collections Parent(Guardians) and documents child(for all Guardians)
                    DocumentReference documentReference = dataStore.collection("Guardians").document(GuardiansID);
                    Map<String, Object> Guardian = new HashMap<>();
                    Guardian.put("Email", emailDB);
                    Guardian.put("ChildName", nameDB);
                    Guardian.put("ChildAge", ageDB);
                    Guardian.put("disease", DiseaseArray);
                    // check the information created successfully or not
                    documentReference.set(Guardian).addOnSuccessListener(aVoid -> {
                        Log.d("TAG", "onSuccess : user is created for" + GuardiansID);
                        // add child automatiqument after sign up of guard
                        Child child = new Child();
                        child.setGuardians_id(GuardiansID);
                        List<Map<String, String>> history = new ArrayList<>();
                        child.setHistory(history);
                        ChildDb.addChild(child);
                    }).addOnFailureListener(e -> Log.d(TAG, "onFailure:user is NOT created  " + e.toString()));
                    // Successful message and display the Home Page
                    Toast.makeText(SignUp.this, "Successfully Sign Up", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));

                } else {// if task not success send the error message
                    Toast.makeText(SignUp.this, " Error!This email is used ", Toast.LENGTH_SHORT).show();

                }
            });

        });
    }


}