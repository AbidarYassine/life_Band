package com.example.lifeband;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    // Declare variables.
    EditText PasswordText,EmailText;
    Button buttonL;

    //Declare an instance of FirebaseAuthentication
    FirebaseAuth databaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("Login");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Fitch each variable with what component fits in the interface.
        PasswordText = findViewById(R.id.password_log);
        EmailText    = findViewById(R.id.email);
        buttonL      = findViewById(R.id.logBtn);

        //initialize the FirebaseAuthentication
        databaseAuth =FirebaseAuth.getInstance();

        //When the user click on Log In button then
        buttonL.setOnClickListener(v -> {
            String emailDB =EmailText.getText().toString();
            String passwordDB =PasswordText.getText().toString();

            // Check evey field if the input is wrongy and send the error message
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(EmailText.getText());
            if(TextUtils.isEmpty(emailDB)||TextUtils.isEmpty(passwordDB)){
                EmailText.setError("This field can not be empty ");
                return;
            }
            if (!matcher.find()){
                EmailText.setError("Invalid Email");
                return;
            }

            //By this method will LogIn for the user by the Email and password in FirebaseAuthentication
            databaseAuth.signInWithEmailAndPassword(emailDB,passwordDB).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    // Successful message and display the Home Page
                    Toast.makeText(LogIn.this,"Successfully Log In ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));

                }else {// if it is NOT Success send the error message
                    Toast.makeText(LogIn.this," Error! Wrong Email or Password ",Toast.LENGTH_SHORT).show();

                }
            });

        });
    }
}