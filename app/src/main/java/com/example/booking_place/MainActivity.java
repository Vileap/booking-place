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
    RecyclerView recyclerView;


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
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        final RecyclerView recyclerView;
        final Adapter[] adapter = new Adapter[1];

        recyclerView = findViewById(R.id.recyclerHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL
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
                                                "$" + objectResponse.get("price").toString() +" per Night")
                                );

                            }

                            adapter[0] = new Adapter(getApplicationContext(), listTopPlace);
                            recyclerView.setAdapter(adapter[0]);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    public Model newPlace (String placeTitle, String placePrice, String address) {

        Model m = new Model();
        m.setTitle(placeTitle);
        m.setDescription(placePrice);
        m.setPrice(address);
        m.setImage(R.drawable.r1room);

        return m;
    }


    private ArrayList<Model> getMyList(){
        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("R1 Room");
        m.setDescription("Phnom Penh");
        m.setPrice("$11 per Night");
        m.setImage(R.drawable.r1room);
        models.add(m);

        m = new Model();
        m.setTitle("Bright Studio");
        m.setDescription("Phnom Penh");
        m.setPrice("$24 per Night");
        m.setImage(R.drawable.brightstudio);
        models.add(m);

        m = new Model();
        m.setTitle("Beautiful Villa 1");
        m.setDescription("Siem Reap");
        m.setPrice("$11 per Night");
        m.setImage(R.drawable.villa_room1);
        models.add(m);

        m = new Model();
        m.setTitle("Carolina");
        m.setDescription("Siem Reap");
        m.setPrice("$13 per Night");
        m.setImage(R.drawable.carolina_room);
        models.add(m);

        return models;
    }
}
