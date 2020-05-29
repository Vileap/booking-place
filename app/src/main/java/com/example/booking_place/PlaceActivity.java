package com.example.booking_place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class PlaceActivity extends Activity {
    private static final String TAG = "PlaceActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        final RecyclerView recyclerView;
        final Adapter[] adapter = new Adapter[1];

        final ArrayList<Model> listTopPlace = new ArrayList<Model>();

        recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));



        ImageButton back = (ImageButton) findViewById(R.id.backMain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button filterButton  = (Button) findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FilterActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle  = intent.getExtras();

        if (bundle != null) {

            String textSearch = (String) bundle.get("TEXTSEARCH");

            mAuth = FirebaseAuth.getInstance();

            db = FirebaseFirestore.getInstance();
            // [END get_firestore_instance]

            // [START set_firestore_settings]
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection("places")
                    .whereEqualTo("address", textSearch)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    Map<String, Object> objectResponse = document.getData();
                                    Log.d(TAG, document.getId() + " =>" + document.getData());
                                    listTopPlace.add(
                                            newPlace(objectResponse.get("name").toString(),
                                                    objectResponse.get("address").toString(),
                                                    "$" + objectResponse.get("price").toString() +" per Night",
                                                    objectResponse.get("image").toString()
                                            )
                                    );
                                }

                                adapter[0] = new Adapter(PlaceActivity.this, listTopPlace, getApplicationContext());
                                recyclerView.setAdapter(adapter[0]);
                            } else {
                                Log.w(TAG, "error getting documents", task.getException());
                            }
                        }
                    });

        }
            //send Query to FirebaseDatabase
    }

    private ArrayList<Model> getMyList(){
        ArrayList<Model> models = new ArrayList<>();
        return models;
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
