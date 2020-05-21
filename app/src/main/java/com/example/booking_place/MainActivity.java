package com.example.booking_place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.booking_place.model.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button viewButton;
        viewButton = findViewById(R.id.viewButton);

        final ArrayList<Model> listTopPlace = new ArrayList<Model>();


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaceActivity.class);
                startActivity(intent);
            }
        });


        Button viewButton2;
        viewButton2 = findViewById(R.id.viewButton2);
        viewButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), PlaceActivity.class);
                startActivity(intent2);
            }
        });


        //send Query to FirebaseDatabase
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        final RecyclerView recyclerView, recyclerView2;
        final Adapter[] adapter = new Adapter[1];

        recyclerView = findViewById(R.id.recyclerHome);
        recyclerView2 = findViewById(R.id.recyclerHome2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL
                , false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL
                , false));


        db.collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Map<String, Object> objectResponse = document.getData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listTopPlace.add(
                                        newPlace(objectResponse.get("name").toString(),
                                                objectResponse.get("address").toString(),
                                                "$" + objectResponse.get("price").toString() +" per Night",
                                                objectResponse.get("image").toString()
                                                )
                                );

                            }

                            adapter[0] = new Adapter(MainActivity.this, listTopPlace);
                            recyclerView.setAdapter(adapter[0]);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Map<String, Object> objectResponse = document.getData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listTopPlace.add(
                                        newPlace(objectResponse.get("name").toString(),
                                                objectResponse.get("address").toString(),
                                                "$" + objectResponse.get("price").toString() +" per Night",
                                                objectResponse.get("image").toString()
                                                )
                                );

                            }

                            adapter[0] = new Adapter(MainActivity.this, listTopPlace);
                            recyclerView2.setAdapter(adapter[0]);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    public Model newPlace (String placeTitle, String placePrice, String address, String imageURL) {

        Model m = new Model();
        m.setTitle(placeTitle);
        m.setDescription(placePrice);
        m.setPrice(address);
         m.setImage(imageURL);

        return m;
    }



}
