package com.example.lifeband;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// MainActivity class it is the( Welcome page )
public class MainActivity extends AppCompatActivity {

    // Define variables.
    Button  SignupBtn , loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fitch each variable with what component fits in the interface.
        SignupBtn = findViewById(R.id.SignBtn);
        loginBtn = findViewById(R.id.loginBtn);

        // If the user click on Log in button will display the Log In page
        loginBtn.setOnClickListener(view -> {
            Intent loginIntent = new Intent(MainActivity.this, LogIn.class);
            startActivity(loginIntent);
        });

        // If the user click on Sign Up button will display the Sign Up page
        SignupBtn.setOnClickListener(view -> {

            Intent signIntent = new Intent(MainActivity.this, SignUp.class);
            startActivity(signIntent);
        });}}


