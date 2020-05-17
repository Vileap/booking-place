package com.example.booking_place;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class PlaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        RecyclerView recyclerView;
        Adapter adapter;

        recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(this, getMyList());
        recyclerView.setAdapter(adapter);


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
