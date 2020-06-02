package com.example.booking_place;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    Button loginB;
    EditText emailEd, passED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        loginB = findViewById(R.id.loginButton);
        emailEd = findViewById(R.id.emailLogin);
        passED = findViewById(R.id.passLogin);


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEd.getText().toString().equals("vileapvong@gmail.com") &&

                        passED.getText().toString().equals("12345")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Bad Credentials, Please login again!",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
