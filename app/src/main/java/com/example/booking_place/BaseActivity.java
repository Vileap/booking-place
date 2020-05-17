package com.example.booking_place;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;

public class BaseActivity extends AppCompatActivity {

    public FirebaseOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
